package fr.eurecom.nerd.pimpStfdNer.model;

import java.util.HashMap;

public class DocumentMap {
	
	private static final DocumentMap instance = new DocumentMap();

	private int counter;
	private HashMap<String, Document> docs;
	
	private DocumentMap(){
		counter = 0;
		docs = new HashMap<>();
	}
	
	public static DocumentMap getInstance(){
		return instance;
	}
	
	public String put(String text){
		counter++;
		Document d = new Document(counter, text);
		docs.put(Integer.toString(counter), d);
		return Integer.toString(counter);
	}
	
	public Document get(String id){
		return docs.get(id);
	}
}
