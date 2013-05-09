package fr.eurecom.nerd.pimpStfdNer.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Document {

	private String id=null;
	private String text=null;
	
	public Document(){
		
	}
	
	public Document(String id, String text){
		this.id=id;
		this.text=text;
	}

	public Document(int counter, String text) {
		this.id=Integer.toString(counter);
		this.text=text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
