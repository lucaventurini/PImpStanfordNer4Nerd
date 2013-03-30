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

public class MyClassifier implements Runnable {
	
	private String inputfile;
	private AbstractSequenceClassifier<CoreLabel> classifier;
	private String outputfile;

	public MyClassifier(String serializedClassifier, String inputfile, String outputfile) throws ClassCastException, IOException, ClassNotFoundException{
		this.inputfile=inputfile;
		this.classifier = CRFClassifier.getClassifier(serializedClassifier);
		this.outputfile=outputfile;		
	}
	
	@Override
	public void run() {
		String fileContents;
		
		try {
			fileContents = IOUtils.slurpFile(inputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter(outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		PrintWriter out = new PrintWriter(fw);
		
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
