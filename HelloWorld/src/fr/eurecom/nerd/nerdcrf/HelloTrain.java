package fr.eurecom.nerd.nerdcrf;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class HelloTrain {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ClassCastException 
	 */
	public static void main(String[] args) throws ClassCastException, IOException, ClassNotFoundException {
		
		//start a threadpool
		ExecutorService threadpool = Executors.newCachedThreadPool();
		
		Properties props = new Properties();
		/* create a new classifier and train */
		
		//create properties
		props.setProperty("map", "word=0,answer=1");
	 	props.setProperty("useClassFeature", "true");
	 	props.setProperty("useWord", "true");
	 	props.setProperty("useNGrams", "true");
	 	props.setProperty("noMidNGrams", "true");
	 	props.setProperty("maxNGramLeng", "6");
	 	props.setProperty("usePrev", "true");
	 	props.setProperty("useNext", "true");
	 	props.setProperty("useSequences", "true");
	 	props.setProperty("usePrevSequences", "true");
	 	props.setProperty("maxLeft", "1");
	 	props.setProperty("useTypeSeqs", "true");
	 	props.setProperty("useTypeSeqs2", "true");
	 	props.setProperty("useTypeySequences", "true");
	 	props.setProperty("wordShape", "chris2useLC");
	 	props.setProperty("useDisjunctive", "true");
	 	
	 	CRFClassifier<CoreLabel> cl1 = new CRFClassifier<CoreLabel>(props);
		
		cl1.train("jane-austen-emma-ch1.tsv");
		
		
		// load a 2nd classifier from memory to compare
	 	AbstractSequenceClassifier<CoreLabel> cl2 = CRFClassifier.getClassifier("classifiers/english.muc.7class.distsim.crf.ser.gz");

		// create a new task
		PImpTask t1 = new PImpTask(cl1, "pg1342.txt", "out_trained.txt");
		PImpTask t2 = new PImpTask(cl2, "pg1342.txt", "out_gold.txt");

		System.out.println("Now I will launch threads...");
		threadpool.execute(t1);
		threadpool.execute(t2);
		
		// close the program
		threadpool.shutdown();
		
		
		try {
			threadpool.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
