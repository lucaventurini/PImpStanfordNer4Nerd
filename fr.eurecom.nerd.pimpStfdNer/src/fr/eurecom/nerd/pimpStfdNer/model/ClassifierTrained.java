package fr.eurecom.nerd.pimpStfdNer.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;

public class ClassifierTrained {

	String id;
	HashMap<String, String> texts; // training texts
	int counter = 0;

	private AbstractSequenceClassifier<CoreLabel> classifier;

	private DocumentReaderAndWriter<CoreLabel> defaultReaderAndWriter;



	public ClassifierTrained(int counter) {
		this.id = Integer.toString(counter);
		texts = new HashMap<String, String>();
	}

	public ClassifierTrained(String id) {
		this.id = id;
		texts = new HashMap<String, String>();

	}
	
	@Deprecated
	public ClassifierTrained(String id, AbstractSequenceClassifier<CoreLabel> classifier){
		this.id = id;
		this.classifier=classifier;
		this.texts = null;
	}

	public String add(InputStream uploadedInputStream) {
		counter++;
		try(Scanner scanner = new Scanner(uploadedInputStream,"UTF-8")){
			String tmp=scanner.useDelimiter("\\A").next();
			texts.put(Integer.toString(counter), tmp);

		}

		//destroy the old classifier
		classifier=null;
		return( Integer.toString(counter));
	}

	public AbstractSequenceClassifier<CoreLabel> getClassifier(){
		if (classifier != null){
			return classifier;
		}
		// initialize a new classifier
		initClassifier();

		// create a documentReaderAndWriter
		defaultReaderAndWriter = classifier.makeReaderAndWriter();

		/* create an ObjectBank from texts */
		String str_texts="";
		for(String s: texts.values()){
			str_texts+=s;
		}
		ObjectBank<List<CoreLabel>> o = classifier.makeObjectBankFromString(str_texts, defaultReaderAndWriter);

		// train
		classifier.train(o);

		//TODO: serialize the classifier
		return classifier;
	}

	private void initClassifier() {
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

		this.classifier = new CRFClassifier<CoreLabel>(props);
	}


}
