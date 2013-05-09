package fr.eurecom.nerd.pimpStfdNer.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Annotation {






	private List<Entity> annotation;

	public Annotation(){
		annotation = new ArrayList<Entity>();
	}

	public Annotation(AbstractSequenceClassifier<CoreLabel> classifier, String text){
		annotation = new ArrayList<Entity>();

		List<List<CoreLabel>> result = classifier.classify(text);

		for (List<CoreLabel> sentence : result) {
			for (CoreLabel word : sentence) {
				annotation.add(new Entity(word.word(), word.get(AnswerAnnotation.class)));
				System.out.println(word.word() + ' ' + word.get(AnswerAnnotation.class));

			}
		}


	}

	@XmlElement(name = "token")
	public List<Entity> getAnnotation() {
		return annotation;
	}


	public void setAnnotation(List<Entity> annotation) {
		this.annotation = annotation;
	}



}
