package fr.eurecom.nerd.pimpStfdNer.model;

import java.io.IOException;
import java.util.HashMap;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class ClassifierMap {
	
	private static final ClassifierMap instance = new ClassifierMap();

	private int counter;
	private HashMap<String, ClassifierTrained> cls;
	private HashMap<String, AbstractSequenceClassifier<CoreLabel>> defaultCls;
	
	private ClassifierMap(){
		counter = 0;
		cls = new HashMap<>();
		defaultCls = new HashMap<>();
		initDefaultCls();
	}
	
	private void initDefaultCls() {
		try {
			AbstractSequenceClassifier<CoreLabel> cl1 = CRFClassifier.getClassifier("NERclassifiers/english.muc.7class.distsim.crf.ser.gz");
			defaultCls.put("default", cl1);
		} catch (ClassCastException | ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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
	
	@Deprecated
	public ClassifierTrained get(String id){		
		return cls.get(id);
	}
	
	public AbstractSequenceClassifier<CoreLabel> getClassifier(String id){
		AbstractSequenceClassifier<CoreLabel> cl = defaultCls.get(id);
		if(cl != null){
			return cl;
		}
		return cls.get(id).getClassifier();
	}
}
