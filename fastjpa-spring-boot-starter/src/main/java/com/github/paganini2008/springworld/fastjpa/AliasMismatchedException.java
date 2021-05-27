package com.github.paganini2008.springworld.fastjpa;

/**
 * 
 * AliasMismatchedException
 *
 * @author Fred Feng
 * @version 1.0
 */
public class AliasMismatchedException extends IllegalArgumentException {

	private static final long serialVersionUID = 2735877872090011720L;

	public AliasMismatchedException(String alias) {
		super(alias);
	}

}
