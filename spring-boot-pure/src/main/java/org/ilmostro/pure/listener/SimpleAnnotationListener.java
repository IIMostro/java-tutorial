package org.ilmostro.pure.listener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
@Component
@Slf4j
public class SimpleAnnotationListener {

	@EventListener(value = {SimpleApplicationEvent.class})
	public void onAnnotationEvent(SimpleApplicationEvent event) {
		log.info("ApplicationListener annotation receive:{}", event);
	}
}
