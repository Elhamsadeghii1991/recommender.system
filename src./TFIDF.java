
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TFIDF {

	  public static class TokenizerMapper
	       extends Mapper<Object, Text, Text, IntWritable>{

	    private final static IntWritable one = new IntWritable(1);
	    private Text word = new Text();

	    public void map(Object key, Text value, Context context                    ) throws IOException, InterruptedException {
	      StringTokenizer itr = new StringTokenizer(value.toString());
	      
	      // seperate paper# from text ===> format paper#@text
	      String val[]= value.toString().split("@");
	      String paperID=val[0];
	      
	      // make bigram with counts
	      // the key part of output contains paperID nd the value will contain the sum(count) of bigram in a specific paper	   
	      for (String ngram : ngrams(2, val[1]))
	      {
	        word.set(paperID+"@"+ngram);
	        context.write(word, one);
	      }
	    }
	  }

	  public static class IntSumReducer
	       extends Reducer<Text,IntWritable,Text,IntWritable> {
	    private IntWritable result = new IntWritable();

	    public void reduce(Text key, Iterable<IntWritable> values,
	                       Context context
	                       ) throws IOException, InterruptedException {
	      int sum = 0;
	      for (IntWritable val : values) {
	        sum += val.get();
	      }
	      result.set(sum);
	      context.write(key, result);
	    }
	  }
	  
	  
	  public static List<String> ngrams(int n, String str) {
	        List<String> ngrams = new ArrayList<String>();
	        String[] words = str.split(" ");
	        for (int i = 0; i < words.length - n + 1; i++)
	            ngrams.add(concat(words, i, i+n));
	        return ngrams;
	    }

	    public static String concat(String[] words, int start, int end) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = start; i < end; i++)
	            sb.append((i > start ? " " : "") + words[i]);
	        return sb.toString();
	    }
	  
	  public static void Run(String inp, String out) throws Exception {
		    Configuration conf = new Configuration();
		    Job job = Job.getInstance(conf, "word count");
		    job.setJarByClass(TFIDF.class);
		    job.setMapperClass(TokenizerMapper.class);
		    job.setCombinerClass(IntSumReducer.class);
		    job.setReducerClass(IntSumReducer.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(IntWritable.class);
		    FileInputFormat.addInputPath(job, new Path(inp));
		    FileOutputFormat.setOutputPath(job, new Path(out));
		    System.exit(job.waitForCompletion(true) ? 0 : 1);
		  }

}
