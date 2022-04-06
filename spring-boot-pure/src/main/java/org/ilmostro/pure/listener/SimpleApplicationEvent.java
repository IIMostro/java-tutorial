package org.ilmostro.pure.listener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.context.ApplicationEvent;

/**
 * @author li.bowei
 */
@Getter
@Setter
@ToString
public class SimpleApplicationEvent extends ApplicationEvent {

	public SimpleApplicationEvent(Object source) {
		super(source);
	}

	public SimpleApplicationEvent(Object source, String id) {
		this(source);
		this.id = id;
	}

	String id;
}
