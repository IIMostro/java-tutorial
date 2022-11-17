package org.ilmostro.basic.chain;

import org.apache.commons.chain.Command;

/**
 * @author li.bowei
 */
public interface CustomCommand<T extends CustomCommandContext<? extends ChainRequest, ? extends ChainResponse>> extends Command {

	boolean execute(T context) throws Exception;

}
