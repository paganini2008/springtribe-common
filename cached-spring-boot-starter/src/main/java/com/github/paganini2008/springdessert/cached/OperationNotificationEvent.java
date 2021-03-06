package com.github.paganini2008.springdessert.cached;

import org.springframework.context.ApplicationEvent;

import com.github.paganini2008.springdessert.cached.base.Cache;

/**
 * 
 * OperationNotificationEvent
 *
 * @author Jimmy Hoff
 * @since 1.0
 */
public class OperationNotificationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -474744672800806045L;

	public OperationNotificationEvent(Cache cache, OperationNotification operationNotification) {
		super(cache);
		this.operationNotification = operationNotification;
	}

	private final OperationNotification operationNotification;

	public OperationNotification getOperationNotification() {
		return operationNotification;
	}

	public Cache getSource() {
		return (Cache) super.getSource();
	}

}
