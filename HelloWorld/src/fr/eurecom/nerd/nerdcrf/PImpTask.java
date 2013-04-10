package fr.eurecom.nerd.nerdcrf;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;

public class PImpTask implements Runnable {
	
	private String inputFile;
	private AbstractSequenceClassifier<CoreLabel> classifier;
	private String outputFile;

	
	@Deprecated
	public PImpTask(String serializedClassifier, String inputFile, String outputFile) throws ClassCastException, IOException, ClassNotFoundException{
		this.inputFile=inputFile;
		this.classifier = CRFClassifier.getClassifier(serializedClassifier);
		this.outputFile=outputFile;		
	}
	
	public PImpTask(AbstractSequenceClassifier<CoreLabel> classifier, String inputFile, String outputFile) throws ClassCastException, IOException, ClassNotFoundException{
		this.inputFile=inputFile;
		this.classifier = classifier;
		this.outputFile=outputFile;		
	}

	@Override
	public void run() {
		String fileContents;
		
		try {
			fileContents = IOUtils.slurpFile(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter(outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		PrintWriter out = new PrintWriter(fw);
		
		System.out.println("Now I will begin the real work.");
        List<List<CoreLabel>> result = classifier.classify(fileContents);
        
        
        
        for (List<CoreLabel> sentence : result) {
          for (CoreLabel word : sentence) {
        	  out.print(word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
          }
          out.println();
        }
	        
	    out.close();
        
        
		
	}

}
