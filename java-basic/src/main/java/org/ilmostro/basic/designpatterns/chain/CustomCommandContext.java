package org.ilmostro.basic.designpatterns.chain;

import org.apache.commons.chain.impl.ContextBase;

/**
 * @author li.bowei
 */
public class CustomCommandContext<T extends ChainRequest, R extends ChainResponse> extends ContextBase {

	private static final String REQUEST_KEY = "request_key";
	private static final String RESPONSE_KEY = "response_key";

	public CustomCommandContext(T request, R response) {
		this.put(REQUEST_KEY, request);
		this.put(RESPONSE_KEY, response);
	}

	public T getRequest(){
		//noinspection unchecked
		return (T) get(REQUEST_KEY);
	}


	public R getResponse(){
		//noinspection unchecked
		return (R) get(RESPONSE_KEY);
	}
}
