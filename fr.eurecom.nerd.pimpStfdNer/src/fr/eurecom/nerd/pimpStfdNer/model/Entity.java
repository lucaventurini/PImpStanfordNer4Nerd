package fr.eurecom.nerd.pimpStfdNer.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entity {
	
	private String word;
	private String label;
	
	public Entity(){
		this.word="";
		this.label="";
	}
	
	public Entity(String word, String label){
		this.word=word;
		this.label=label;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	

}
