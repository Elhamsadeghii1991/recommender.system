

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class ReducerJoin
extends Reducer<Text, Text, Text, Text> {
@Override
public void reduce(Text key, Iterable<Text> values,
 Context context)
 throws IOException, InterruptedException {

 // compute the count of each keywords issued by a user
 // the outputformat =>   key => userId,Keyword value=> count 
 String sum = null;
 for (Text value : values)
	 sum+=","+value.toString();
 
 
  context.write(new Text(key), new Text(sum));
 
}
}