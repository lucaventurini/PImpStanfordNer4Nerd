package fr.eurecom.nerd.nerdcrf;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class HelloThread {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ClassCastException 
	 */
	public static void main(String[] args) throws ClassCastException, IOException, ClassNotFoundException {
		
		//start a threadpool
		ExecutorService threadpool = Executors.newFixedThreadPool(5);		
		
		// load classifiers in memory
	 	AbstractSequenceClassifier<CoreLabel> cl1 = CRFClassifier.getClassifier("classifiers/english.muc.7class.distsim.crf.ser.gz");
	 	AbstractSequenceClassifier<CoreLabel> cl2 = CRFClassifier.getClassifier("classifiers/english.all.3class.distsim.crf.ser.gz");
		
		// create a new task
		PImpTask t1 = new PImpTask(cl1, "la_divin.txt", "out_7class.txt");
		PImpTask t2 = new PImpTask(cl1, "la_divin2.txt", "out_3class.txt");

		System.out.println("Now I will launch threads...");
		threadpool.execute(t1);
		threadpool.execute(t2);

	}

}
