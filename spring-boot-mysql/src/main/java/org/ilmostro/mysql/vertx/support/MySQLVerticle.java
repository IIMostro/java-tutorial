package org.ilmostro.mysql.vertx.support;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

import org.springframework.stereotype.Component;

/**
 * @author li.bowei
 */
@Component
public class MySQLVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> promise) throws Exception {
//		MySQLConnectOptions connectOptions = new MySQLConnectOptions()
//				.setHost()
//				.setPort()
//				.setDatabase("the-db")
//				.setUser("user")
//				.setPassword("secret");
//		PoolOptions options = new PoolOptions();
//		MySQLPool pool = MySQLPool.pool(vertx, connectOptions, options);
//		promise.complete();
	}
}
