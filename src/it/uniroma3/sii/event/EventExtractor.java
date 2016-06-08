package it.uniroma3.sii.event;

import java.io.UnsupportedEncodingException;
import java.util.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;


public class EventExtractor {
	private Properties props;
	private StanfordCoreNLP pipeline;
	private Annotation annotation;
	private Map<String, Map<String, String>> token2tagValue;
	
	public EventExtractor() {
		this.props = new Properties();
		this.props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
		this.pipeline = new StanfordCoreNLP(this.props);
	}
	
	private Map<String, Map<String, String>> tweetTokenizitation(String tweet) {
		try {
			byte[] b = tweet.getBytes("ASCII");
			tweet = new String(b, "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		this.annotation = new Annotation(tweet);
		this.token2tagValue = new HashMap<String, Map<String, String>>();
		this.pipeline.annotate(this.annotation);
		
		List<CoreLabel> txtAnn = this.annotation.get(CoreAnnotations.TokensAnnotation.class);
		for (CoreLabel c: txtAnn) {
	    	if (c.tag().equals("NNP") && c.ner().equals("LOCATION") || c.tag().equals("NNP") || c.tag().equals("CD") && c.ner().equals("DATE")){
	    		HashMap<String, String> temp = new HashMap<String, String>();
	    		temp.put("text", c.originalText());							
	    		temp.put("pos", c.tag());
	    		temp.put("ner", c.ner());
	    		temp.put("date", c.get(edu.stanford.nlp.ling.CoreAnnotations.NormalizedNamedEntityTagAnnotation.class));
	    		this.token2tagValue.put(c.originalText(), temp);
	    	}
	    }
		
		return token2tagValue;
	}
	
	public boolean isAnEvent() {
		boolean date = false;
		boolean nnp = false;
		boolean location = false;
		for(Map<String, String> map : this.token2tagValue.values()) {
			if(map.get("pos") != null || map.get("ner") != null || map.get("date") != null) {
				if(map.get("ner").equals("LOCATION")) 
					nnp = true;
				else{ if(map.get("ner").equals("DATE"))
					date = true;
				else if(map.get("pos").equals("NNP"))
					location = true;
			}
			}
	
		}
		return date && location && nnp;
	}
	
	public Event eventExtraction(String tweet) {
		this.tweetTokenizitation(tweet);
		Event event =  new Event();
		if(this.isAnEvent()){
			for(Map<String, String> map : this.token2tagValue.values()) {
				if(map.get("ner").equals("LOCATION"))
					event.setLuogo(event.getLuogo() + " " + map.get("text"));
				else{ if(map.get("ner").equals("DATE"))
					event.setData(map.get("date"));
				else if(map.get("pos").equals("NNP"))
					event.setNome(event.getNome() + " " + map.get("text"));
				}
			}
		}
		return event;
	}
	
}
