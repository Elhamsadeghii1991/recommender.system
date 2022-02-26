import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Recommender extends Configured implements Tool{
	public int run(String[] args) throws Exception
	{
		
		// join the vector of Dataset of Papers and input paper
				 Job jobJoin = new Job();
				 jobJoin.setJarByClass(Recommender.class);
				 jobJoin.setJobName("Recommnder-join");
				 
				 MultipleInputs.addInputPath(jobJoin,new Path( args[0]), TextInputFormat.class, MapperJoin.class);
			     MultipleInputs.addInputPath(jobJoin, new Path( args[1]), TextInputFormat.class, MapperJoin.class);
			     FileOutputFormat.setOutputPath(jobJoin,new Path("Join_Output"));
			     jobJoin.setReducerClass(ReducerJoin.class);
				 
			     jobJoin.setMapOutputKeyClass(Text.class);
			     jobJoin.setMapOutputValueClass(Text.class);
			     jobJoin.setOutputKeyClass(Text.class);
			     jobJoin.setOutputValueClass(Text.class);				 				 				 				 				 				
			     jobJoin.waitForCompletion(true);
				 

		 
		 // job 3 = produce initial profile of resource
		 // 
		// job 3 ==> count the number of times a tag related to the a resource for specefic user
				 Job jobRecommnder = new Job();
				 jobRecommnder.setJarByClass(Recommender.class);
				 jobRecommnder.setJobName("Yandex Based On Tags Job3");
				 
				 jobRecommnder.setMapperClass(MapperRecommender.class);
				 jobRecommnder.setReducerClass(ReducerRecommender.class);

				 jobRecommnder.setOutputKeyClass(Text.class);
				 jobRecommnder.setOutputValueClass(IntWritable.class);
				 
				 


				 FileInputFormat.addInputPath(jobRecommnder, new Path("Join_Output"));
				 FileOutputFormat.setOutputPath(jobRecommnder,new Path( "Recommendation") );
				 jobRecommnder.waitForCompletion(true);
				 
	}
}