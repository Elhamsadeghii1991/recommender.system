
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class MapperJoin extends
 Mapper<LongWritable, Text, Text, Text> {
	String id="-1"; // for user id
@Override
 public void map(LongWritable key, Text value, Context context)
 throws IOException, InterruptedException {
	
	
	// get paperID
	String parts[]=key.toString().split("@");
	
	// output >> (bigram,paperID)
    context.write(new Text(parts[1]),new Text(parts[0]));
	}
}// map method

