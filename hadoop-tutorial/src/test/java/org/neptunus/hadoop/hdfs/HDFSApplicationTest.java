package org.neptunus.hadoop.hdfs;

import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

/**
 * @author li.bowei
 */
@Slf4j
public class HDFSApplicationTest {

	private static final String URL = "hdfs://192.168.0.105:8020";
	private static FileSystem fs;
	private static final Configuration configuration = new Configuration();

	@Before
	public void before() throws Exception{
		// 设置客户端只存储一个副本
		configuration.set("dfs.replication", "1");
		// 这个user需要传递有权限的用户
		fs = FileSystem.get(new URI(URL), configuration, "ubuntu");
	}

	@Test
	public void exist() throws Exception{
		boolean exists = fs.exists(new Path("/README.txt"));
		log.info("/README.txt exist ? {}", exists);
	}

	@Test
	public void mkdir() throws Exception{
		boolean mkdirs = fs.mkdirs(new Path("/api"));
		log.info("/api create: {}", mkdirs);
	}

	@Test
	public void create() throws Exception{
		FSDataOutputStream fos = fs.create(new Path("/api/Test.txt"));
		fos.write("Hello World!".getBytes());
		fos.flush();
		fos.close();
	}

	@Test
	public void text() throws Exception{
		FSDataInputStream open = fs.open(new Path("/api/GONE-WITH-THE-WIND-WORD-COUNT.txt"));
		IOUtils.copy(open, System.out, 1024);
	}

	@Test
	public void bigData() throws Exception{
		//TODO 利用MMAP读取文件后放入HDFS
		try(RandomAccessFile memoryAccessFile = new RandomAccessFile("/Users/li.bowei/Downloads/hadoop-3.2.3.tar.gz", "rw")){
			FileChannel fileChannel = memoryAccessFile.getChannel();
			MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());
			// 设置内存初始偏移段
			int offset = 0;
		}
	}

	@Test
	public void put() throws Exception{
		fs.copyFromLocalFile(new Path("/Users/li.bowei/workspaces/java/ilmostro/java-tutorial/hadoop-tutorial/src/main/resources/1628065496.txt"),
				new Path("/api/1628065496.txt"));
	}


	@Test
	public void ls() throws Exception{
		FileStatus[] statuses = fs.listStatus(new Path("/"));
		for (FileStatus status : statuses) {
			log.info(":{}", status);
		}
	}

}
