package org.neptunus.hadoop.hdfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @author li.bowei
 */
@Slf4j
public class HDFSWordCountApplication {

	public static void main(String[] args) throws Exception {
		FileSystem fs = HDFSUtils.getFileSystem();
		FSDataInputStream fis = fs.open(new Path("/api/1628065496.txt"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		String line = "";
		Map<String, Integer> counter = new HashMap<>();
		while((line = reader.readLine()) != null){
			String[] s = StringUtils.split(line, " ");
			for (String value : s) {
				counter.compute(value, (v1, v2) -> v2 == null ? 1: v2 + 1);
			}
		}

		FSDataOutputStream oos = fs.create(new Path("/api/GONE-WITH-THE-WIND-WORD-COUNT.txt"));
		for (Map.Entry<String, Integer> entry : counter.entrySet()) {
			oos.write((entry.getKey() + "\t" + entry.getValue() + "\n").getBytes());
		}
		oos.close();
		fis.close();
		fs.close();
	}
}
