package org.neptunus.hadoop.hdfs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Optional;
import java.util.Properties;

import com.google.common.base.Charsets;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

/**
 * @author li.bowei
 */
public class HDFSUtils {

	private static final Properties properties = new Properties();

	static{
		Optional.of(HDFSUtils.class)
				.map(Class::getClassLoader)
				.map(v1 -> v1.getResourceAsStream("hdfs.properties"))
				.map(v1 -> new InputStreamReader(v1, Charsets.UTF_8))
				.ifPresent(v1 -> {
					try {
						properties.load(v1);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				});
		System.setProperty("HADOOP_USER_NAME", properties.get("USER").toString());
	}

	private static String getHDFSUrl(){
		return properties.getOrDefault("URL", "").toString();
	}
	private static String getSystemUser(){
		return properties.getOrDefault("USER", "").toString();
	}

	private static String getReplication(){
		return properties.getOrDefault("REPLICATION", "1").toString();
	}


	public static FileSystem getFileSystem(){
		try {
			return getFileSystem(getHDFSUrl(), "ubuntu");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static FileSystem getFileSystem(String url, String user) throws Exception{
		return FileSystem.get(new URI(url), getConfiguration(), user);
	}

	public static Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", getHDFSUrl());
		configuration.set("dfs.replication", getReplication());
		return configuration;
	}

	public static void main(String[] args) {
		Configuration configuration = getConfiguration();
		System.out.println(configuration);
	}
}
