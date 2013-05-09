package fr.eurecom.nerd.pimpStfdNer.model;

import java.util.HashMap;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class AnnotationMap {
	
	private static final AnnotationMap instance = new AnnotationMap();

	private int counter;
	private HashMap<String, Annotation> annotations;
	
	private AnnotationMap(){
		counter = 0;
		annotations = new HashMap<>();
	}
	
	public static AnnotationMap getInstance(){
		return instance;
	}
	
	public String put(AbstractSequenceClassifier<CoreLabel> classifier, String text){
		counter++;
		Annotation a = new Annotation(classifier, text);
		annotations.put(Integer.toString(counter), a);
		return Integer.toString(counter);
	}
	
	public Annotation get(String id){
		return annotations.get(id);
	}
}
