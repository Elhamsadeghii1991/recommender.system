import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.tartarus.martin.Stemmer;

public class RemoveStopWord {

	// delete stopwords
	// function deletStopWord() for remove all stopword in your "stopword.txt"
	public static ArrayList<String> deletStopWord(Set stopWords,ArrayList<String> arraylist){
	        System.out.println(stopWords.contains("?"));
	        ArrayList<String> NewList = new ArrayList<String>();
	        int i=0;
	        while(i < arraylist.size() ){
	            if(!stopWords.contains(arraylist.get(i))){
	                NewList.add((String) arraylist.get(i));
	            }
	            i++;        
	            }
	        System.out.println(NewList);
	        
	        return NewList;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static String Stem (char[] str)
	{
		Stemmer stm=new Stemmer();
		stm.add(str,str.length);
		stm.stem();
		return stm.toString();
		
	}
	
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		 TFIDF.Run("inp.txt", "out");
		
		// define stopWords
		Set<String> stopWords = new LinkedHashSet<String>();
        BufferedReader SW= new BufferedReader(new FileReader("StopWord.txt"));
        for(String line;(line = SW.readLine()) != null;)
           stopWords.add(line.trim());
        SW.close();
		
        
        //
        //extract lines and words , send it to a function to delete stopwords
       
        BufferedReader br = new BufferedReader(new FileReader("inp.txt"));
        String line2 = br.readLine();
        while( line2!= null)
        {
        	ArrayList<String> arrayList = new ArrayList<String>();   
        	String[] words=line2.split("[\\p{Punct}\\s]+");
        	for (int i=0;i<words.length;i++)
        	{
        		     		
        		if (words[i].trim().length()>0)
        		  arrayList.add(words[i].toLowerCase());
        	}
        	
        	// delete stop word . the new line is in the newArrayList
        	ArrayList<String> newArrayList = new ArrayList<String>();
        	newArrayList=  deletStopWord(stopWords,arrayList);
        	
        	line2 = br.readLine();
        }
        
        // stem with porter algorithm 
        char[] t=("I am doing").toCharArray();
        String txt=Stem(t);

	}

}
