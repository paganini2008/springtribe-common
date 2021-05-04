package com.github.paganini2008.springworld.fastjpa;

/**
 * 
 * JpaJoin
 * 
 * @author Jimmy Hoff
 *
 * @version 1.0
 */
public interface JpaJoin {

	default <X> JpaQuery<X> join(String attributeName, String alias) {
		return join(attributeName, alias, null);
	}

	default <X> JpaQuery<X> leftJoin(String attributeName, String alias) {
		return leftJoin(attributeName, alias, null);
	}

	default <X> JpaQuery<X> rightJoin(String attributeName, String alias) {
		return rightJoin(attributeName, alias, null);
	}

	<X> JpaQuery<X> join(String attributeName, String alias, Filter on);

	<X> JpaQuery<X> leftJoin(String attributeName, String alias, Filter on);

	<X> JpaQuery<X> rightJoin(String attributeName, String alias, Filter on);

}