
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class MapperRecommender extends
 Mapper<LongWritable, Text, Text, Text> {
	String id="-1"; // for user id
@Override
 public void map(LongWritable key, Text value, Context context)
 throws IOException, InterruptedException {
	
	
	
	// output >> (bigram,paperID)
    context.write(new Text(value),new Text(value));
	}
}// map method

