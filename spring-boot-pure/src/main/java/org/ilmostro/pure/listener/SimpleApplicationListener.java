package org.ilmostro.pure.listener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
@Component
@Slf4j
public class SimpleApplicationListener implements ApplicationListener<SimpleApplicationEvent> {

	@Override
	public void onApplicationEvent(@NonNull SimpleApplicationEvent event) {
		log.info("ApplicationListener interface receive:{}", event);
	}
}
