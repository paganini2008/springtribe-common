package com.github.paganini2008.springworld.fastjpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import com.github.paganini2008.devtools.StringUtils;

/**
 * 
 * JoinModel
 *
 * @author Fred Feng
 * @version 1.0
 */
public class JoinModel<X, Y> implements Model<Y> {

	private final Join<X, Y> join;
	private final String name;
	private final Metamodel metamodel;
	private final Model<X> model;

	JoinModel(Join<X, Y> join, String name, Metamodel metamodel, Model<X> model) {
		this.join = join;
		this.name = name;
		this.metamodel = metamodel;
		this.model = model;
	}

	@Override
	public Class<?> getRootType() {
		return model.getRootType();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class<Y> getType() {
		return (Class<Y>) join.getAttribute().getJavaType();
	}

	@Override
	public boolean isManaged(Class<?> type) {
		if (getType().equals(type)) {
			return true;
		}
		return model.isManaged(type);
	}

	@Override
	public EntityType<Y> getEntityType() {
		return metamodel.entity(getType());
	}

	@Override
	public boolean hasAttribute(String name, String attributeName) {
		return this.name.equals(name) && StringUtils.isNotBlank(attributeName) ? true : model.hasAttribute(name, attributeName);
	}

	@Override
	public <T> Path<T> getAttribute(String attributeName) {
		return join.get(attributeName);
	}

	@Override
	public <T> Path<T> getAttribute(String name, String attributeName) {
		if (this.name.equals(name) && StringUtils.isNotBlank(attributeName)) {
			return join.get(attributeName);
		}
		return model.getAttribute(name, attributeName);
	}

	@Override
	public Root<?> getRoot() {
		return model.getRoot();
	}

	@Override
	public List<Selection<?>> getSelections(String name) {
		if (this.name.equals(name)) {
			List<Selection<?>> selections = new ArrayList<>();
			selections.add(join.alias(name));
			return selections;
		}
		return model.getSelections(name);
	}

	@Override
	public List<JpaAttributeDetail> getAttributeDetails(String name) {
		if (this.name.equals(name)) {
			List<JpaAttributeDetail> details = new ArrayList<JpaAttributeDetail>();
			for (SingularAttribute<? super Y, ?> attribute : getEntityType().getSingularAttributes()) {
				details.add(new JpaAttributeDetailImpl<>(attribute));
			}
			return details;
		}
		return model.getAttributeDetails(name);
	}

	@Override
	public <Z> Model<Z> join(String attributeName, String name, Predicate on) {
		Join<Y, Z> join = this.join.join(attributeName, JoinType.INNER);
		if (on != null) {
			join.on(on);
		}
		return new JoinModel<Y, Z>(join, name, metamodel, this);
	}

	@Override
	public <Z> Model<Z> leftJoin(String attributeName, String name, Predicate on) {
		Join<Y, Z> join = this.join.join(attributeName, JoinType.LEFT);
		if (on != null) {
			join.on(on);
		}
		return new JoinModel<Y, Z>(join, name, metamodel, this);
	}

	@Override
	public <Z> Model<Z> rightJoin(String attributeName, String name, Predicate on) {
		Join<Y, Z> join = this.join.join(attributeName, JoinType.RIGHT);
		if (on != null) {
			join.on(on);
		}
		return new JoinModel<Y, Z>(join, name, metamodel, this);
	}

	@Override
	public <S> Model<S> sibling(Model<S> sibling) {
		return new SiblingModel<Y, S>(sibling, this);
	}

}
