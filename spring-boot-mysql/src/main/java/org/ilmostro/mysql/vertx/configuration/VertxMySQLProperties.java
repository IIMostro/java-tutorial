package org.ilmostro.mysql.vertx.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author li.bowei
 */
@ConfigurationProperties(prefix = "spring.vertx.mysql")
public class VertxMySQLProperties {

	private String host;
	private Integer port;
	private String username;
	private String password;
	private String db;

	String getHost() {
		return host;
	}

	void setHost(String host) {
		this.host = host;
	}

	Integer getPort() {
		return port;
	}

	void setPort(Integer port) {
		this.port = port;
	}

	String getUsername() {
		return username;
	}

	void setUsername(String username) {
		this.username = username;
	}

	String getPassword() {
		return password;
	}

	void setPassword(String password) {
		this.password = password;
	}

	String getDb() {
		return db;
	}

	void setDb(String db) {
		this.db = db;
	}
}
