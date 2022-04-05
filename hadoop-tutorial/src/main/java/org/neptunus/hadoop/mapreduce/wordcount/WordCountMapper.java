package org.neptunus.hadoop.mapreduce.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 *  KEYIN: 		Map任务读数据的KEY类型，offset，是每行数据起始的偏移量
 *  VALUEIN:	Map任务读数据的KEY类型，一行字符串
 *
 *  KEYOUT:		Map自定义任务输出的KEY类型
 *  VALUEOUT:	Map自定义任务输出的VALUE类型
 *
 *  这里使用的都是Hadoop的数据类型
 * @author li.bowei
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] words = value.toString().split(" ");
		for (String word : words) {
			context.write(new Text(word), new LongWritable(1));
		}
	}
}
