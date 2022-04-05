package org.neptunus.hadoop.mapreduce.wordcount;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.neptunus.hadoop.hdfs.HDFSUtils;

/**
 * @author li.bowei
 */
public class WordCountApplication {

	public static void main(String[] args) throws Exception {

		//启动类
		Job job = Job.getInstance(HDFSUtils.getConfiguration());

		//主类
		job.setJarByClass(WordCountApplication.class);

		// Mapper，Reducer类
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		/*
			添加一个Combiner操作，
			这个操作就是在Mapper端进行一次聚合。以减少Mapper端传输的压力
			因为Mapper每次都需要网络传输到Reducer端。
			所以在Mapper端进行一次Combiner操作是可以减少网络IO的
		 */
		job.setCombinerClass(WordCountReducer.class);

		// Mapper,Reducer输入输出类
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);

		Path path = new Path("/wordcount/output");
		FileSystem fs = HDFSUtils.getFileSystem();
		if (fs.exists(path)) fs.delete(path, true);

		FileInputFormat.setInputPaths(job, new Path("/wordcount/input/GONE-WITH-THE-WIND.txt"));
		FileOutputFormat.setOutputPath(job, path);

		boolean res = job.waitForCompletion(true);
		System.exit(res ? 0 : -1);
	}
}
