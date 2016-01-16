package analyzer;

import java.util.HashMap;

/**
 * TextObject-class representing a Text after parsing.
 * Used in training and analyzing
 * 
 * @author Hex-3-En aka. Patrick Willnow & Marcel Selle
 * @version FINAL RELEASE
 */
public class TextObject {
	
	/**
	 * HashMap containing the vocabulary and the per-word-count
	 */
	private HashMap<String, Double> wordCount;
	
	/**
	 * HashMap containing type-counts found for this text
	 */
	private HashMap<String, Double> typeCount;
	
	/**
	 * String containing name of the text-file (actually unused)
	 */
	@SuppressWarnings("unused")
	private String name;
	
	/**
	 * Hard-coded String of signs to ignore for calculating word-count
	 */
	private String punctuations = ".,;:?!»„-–()[]{}";

	/**
	 * Constructor initializing fields
	 * 
	 * @param n name of text as string
	 */
	public TextObject(String n){
		name = n;
		wordCount = new HashMap<String, Double>();
		typeCount = new HashMap<String, Double>();
	}

	/**
	 * getter-method of word-count-map
	 * 
	 * @return word-counts as HashMap
	 */
	public HashMap<String, Double> getWordCount() {
		return wordCount;
	}

	/**
	 * getter-method of type-counts
	 * 
	 * @return type-counts as HashMap
	 */
	public HashMap<String, Double> getTypeCount() {
		return typeCount;
	}

	/**
	 * Method for adding a word to map or counting +1 if exists.
	 * Called by parser
	 * 
	 * @param dummy word to add as string
	 */
	public void addWord(String dummy) {
		if(wordCount.containsKey(dummy)){
			wordCount.put(dummy, wordCount.get(dummy) + 1);
		}else{
			wordCount.put(dummy, 1.0);
		}
	}
	
	/**
	 * Method for calculating overall word-count from map
	 * 
	 * @return overall word-count of text as double
	 */
	public double getTotalWords(){
		double ret = 0;
		for(String key : wordCount.keySet()){
			if(!punctuations.contains(key)){
				ret += wordCount.get(key);
			}
		}
		return ret;
	}
	
	/**
	 * method for adding a type-count or adding count-value if type exists
	 * 
	 * @param dummy type to add or raise value as string
	 * @param dummyVal value to add as double
	 */
	public void addType(String dummy, double dummyVal) {
		if(typeCount.containsKey(dummy)){
			typeCount.put(dummy, typeCount.get(dummy) + dummyVal);
		}else{
			typeCount.put(dummy, dummyVal);
		}
	}
}
