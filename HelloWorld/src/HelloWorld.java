import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ie.crf.CRFClassifier;

import java.util.List;
import java.io.IOException;

public class HelloWorld {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";

		//String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";

		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);

		String s1 = "Good afternoon Luca Venturini and Luigi, how are you today?";
		
		int i=0;
        for (List<CoreLabel> lcl : classifier.classify(s1)) {
          for (CoreLabel cl : lcl) {
            System.out.println(i++ + ":");
            System.out.println(cl);
          }
        }
        
        String fileContents = IOUtils.slurpFile("sample_win8.txt");
        List<List<CoreLabel>> out = classifier.classify(fileContents);
        for (List<CoreLabel> sentence : out) {
          for (CoreLabel word : sentence) {
            System.out.print(word.word() + '/' + word.get(AnswerAnnotation.class) + ' ');
          }
          System.out.println();
        }

	}
	
	

}
