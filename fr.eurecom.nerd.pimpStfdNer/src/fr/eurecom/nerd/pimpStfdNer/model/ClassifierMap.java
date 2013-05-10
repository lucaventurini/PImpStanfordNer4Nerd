package fr.eurecom.nerd.pimpStfdNer.model;

import java.util.HashMap;

public class ClassifierMap {
	
	private static final ClassifierMap instance = new ClassifierMap();

	private int counter;
	private HashMap<String, ClassifierTrained> cls;
	
	private ClassifierMap(){
		counter = 0;
		cls = new HashMap<>();
	}
	
	public static ClassifierMap getInstance(){
		return instance;
	}
	
	public String put(){
		counter++;
		ClassifierTrained c = new ClassifierTrained(counter);
		cls.put(Integer.toString(counter), c);
		return Integer.toString(counter);
	}
	
	public ClassifierTrained get(String id){
		return cls.get(id);
	}
}
