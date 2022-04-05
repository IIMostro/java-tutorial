package org.neptunus.hadoop.mapreduce.wordcount;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author li.bowei
 */
public class WordCountReducer extends Reducer<Text, LongWritable,Text, LongWritable> {

	/**
	 * map的输出段到这里
	 * reducer1: (hello, 1)(hello, 1)(hello, 1)
	 * reducer2: (world, 1)(world, 1)
	 *
	 * @param key key hello, word
	 * @param values 可迭代的values (1,1,1) (1,1)
	 * @param context 上下文信息
	 * @throws IOException Exception
	 * @throws InterruptedException Exception
	 */
	@Override
	protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
		Iterator<LongWritable> iterator = values.iterator();
		long ans = 0L;
		while(iterator.hasNext()) ans += iterator.next().get();
		context.write(key, new LongWritable(ans));
	}
}
