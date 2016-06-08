package it.uniroma3.sii.classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.uniroma3.sii.naiveBayes.NaiveBayes;

public class NaiveClassifier {
	
	public static void classification(NaiveBayes nb){
		ArrayList<String> sport = DataReader.getData("sport");
		ArrayList<String> politics = DataReader.getData("politics");
		ArrayList<String> music = DataReader.getData("music");
		
		
		Map<String, String[]> training = new HashMap<>();
		String[] data_sport = new String[114];
		String[] data_politics = new String[130];
		String[] data_music = new String[200];
		
		int i = 0;
		for(String t : sport){
			data_sport[i] = t;
			i++;
		}
		int j = 0;
		for(String t : politics){
			data_politics[j] = t;
			j++;
		} 
		int k = 0;
		for(String t : music){
			data_music[k] = t;
			k++;
		} 
		
		training.put("politics", data_politics);
		training.put("sport", data_sport);
		training.put("music", data_music);
		nb.setChisquareCriticalValue(0.001);
		nb.train(training);
		//NaiveBayesKnowledgeBase knowledgeBase = nb.getKnowledgeBase();
		//nb = new NaiveBayes(knowledgeBase);
		/*String exampleEn = "basket";
		String outputEn = nb.predict(exampleEn);
		System.out.format("The sentense \"%s\" was classified as \"%s\".%n", exampleEn, outputEn);  */
	}
}
