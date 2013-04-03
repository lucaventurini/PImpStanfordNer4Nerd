package fr.eurecom.nerd.nerdcrf;

import java.io.IOException;

public class HelloThread {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ClassCastException 
	 */
	public static void main(String[] args) throws ClassCastException, IOException, ClassNotFoundException {
		MyClassifier cl = new MyClassifier("classifiers/english.muc.7class.distsim.crf.ser.gz", "liberty.txt", "out_7class.txt");
		//MyClassifier cl = new MyClassifier("classifiers/english.muc.7class.distsim.crf.ser.gz", "la_divin.txt", "out_7class.txt");

		Thread t = new Thread(cl);
		
		MyClassifier cl2 = new MyClassifier("classifiers/english.all.3class.distsim.crf.ser.gz", "liberty.txt", "out_3class.txt");
		//MyClassifier cl2 = new MyClassifier("classifiers/english.all.3class.distsim.crf.ser.gz", "la_divin2.txt", "out_3class.txt");

		Thread t2 = new Thread(cl2);
		
		t.start();
		t2.start();

	}

}
