package org.neptunus.hadoop.hdfs;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

/**
 * @author li.bowei
 */
public class HDFSUtils {

	public static FileSystem getFileSystem(){
		try {
			return getFileSystem("hdfs://192.168.0.105:8020", "ubuntu");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static FileSystem getFileSystem(String url, String user) throws Exception{
		return FileSystem.get(new URI(url), getConfiguration(1), user);
	}

	private static Configuration getConfiguration(int replication){
		Configuration configuration = new Configuration();
		configuration.set("dfs.replication", String.valueOf(replication));
		return configuration;
	}

}
