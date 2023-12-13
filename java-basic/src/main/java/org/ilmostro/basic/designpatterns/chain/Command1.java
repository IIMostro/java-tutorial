package org.ilmostro.basic.designpatterns.chain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Context;

/**
 * @author li.bowei
 */
@Slf4j
public class Command1 implements CustomCommand<CustomCommandContext<ChainRequest, ChainResponse>> {

	@Override
	public boolean execute(Context context) {
		return false;
	}


	@Override
	public boolean execute(CustomCommandContext<ChainRequest, ChainResponse> context) throws Exception {
		final ChainRequest request = context.getRequest();
		final ChainResponse response = context.getResponse();
		return false;
	}
}
