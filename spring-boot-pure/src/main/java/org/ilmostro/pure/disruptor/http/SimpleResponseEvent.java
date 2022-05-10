package org.ilmostro.pure.disruptor.http;

import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

/**
 * @author li.bowei
 */
@Getter
@Setter
public class SimpleResponseEvent {

	private HttpServletResponse response;
}
