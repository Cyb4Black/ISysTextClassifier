package analyzer;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * TextClass-class that represents one text-class found in source-directory
 * Holds information about word-counts and TypeObjects that represent the word-types (dict-types) counted for classification
 * 
 * @author Hex-3-En aka. Patrick Willnow & Marcel Selle
 * @version FINAL RELEASE
 *
 */
public class TextClass {
	
	/**
	 * HashMap containing the overall vocabulary of trained texts and each words count 
	 */
	private HashMap<String, Double> wordCount;
	
	/**
	 * HashMap containing all types found in trained texts as TypeObjects
	 */
	private HashMap<String, TypeObject> typeCount;
	
	/**
	 * doubles holding the biggest, smallest and overall(sum of all texts) textsize (count of words per text)
	 */
	private double textSizeSum, textSizeMax, textSizeMin;
	
	/**
	 * number of texts trained
	 */
	private int textCount;
	
	/**
	 * name of this text-type (e.g. "Filme" or "Nachrichten")
	 */
	private String name;
	
	/**
	 * Hardcoded String containing elements to ignore in list when calculating text-size
	 */
	private String punctuations = ".,;:?!»„-–()[]{}";
	
	/**
	 * Constructor of class, initializing all fields
	 * 
	 * @param n name to set for this class, taken from foldername of text-class
	 * @param inList initial list of found dict-types in source-folder,
	 * holding initial empty TypeObjects
	 */
	public TextClass(String n, LinkedList<DictType> inList){
		name = n;
		textCount = 0;
		textSizeSum = 0;
		textSizeMin = 99999;
		textSizeMax = 0;
		wordCount = new HashMap<String, Double>();
		typeCount = new HashMap<String, TypeObject>();
		for(DictType dt : inList){
			typeCount.put(dt.getName(), new TypeObject(dt.getName()));
		}
	}
	
	/**
	 * method to calculate the total words in this text-class
	 * 
	 * @return total count of words as double
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
	 * getter method for TypeCounts
	 * 
	 * @return this typeCounts as HashMap
	 */
	public HashMap<String, TypeObject> getTypeCount(){
		return this.typeCount;
	}
	
	/**
	 * method to calculate the average size of all text learned
	 * 
	 * @return average text-size as double
	 */
	public double getAverageTextSize(){
		return this.textSizeSum / textCount;
	}
	
	/**
	 * method to add a text in training-phase,
	 * calling sub-methods used to learn different values
	 * 
	 * @param to TextObject representing the Text to learn
	 */
	public void addText(TextObject to){
		addWordCount(to.getWordCount());
		addTypeCount(to);
		addTextSize(to.getTotalWords());
		textCount++;
	}
	
	/**
	 * sub-method of learning-process,
	 * adding the text-size
	 * 
	 * @param size text-size of text to learn
	 */
	private void addTextSize(double size){
		textSizeSum += size;
		if(size > textSizeMax){
			textSizeMax = size;
		}else if(size < textSizeMin){
			textSizeMin = size;
		}
	}
	
	/**
	 * sub-method of learning-process,
	 * adding the word-count of text to learn
	 * 
	 * @param newWordCount HasMap of word-counts to learn
	 */
	public void addWordCount(HashMap<String, Double> newWordCount) {
		for(String key : newWordCount.keySet()){
			if(wordCount.containsKey(key)){
				double newVal = wordCount.get(key) + newWordCount.get(key);
				wordCount.put(key, newVal);
			}else{
				wordCount.put(key, newWordCount.get(key));
			}
		}
	}
	
	/**
	 * sub-method of learning-process,
	 * adding the type-count of text to learn
	 * @param to TextObject to learn the type-counts from
	 */
	public void addTypeCount(TextObject to) {
		HashMap<String, Double> newTypeCount = to.getTypeCount();
		for(String key : this.typeCount.keySet()){
			if(newTypeCount.get(key) == null){
				this.typeCount.get(key).addVal(0);
			}else{
				this.typeCount.get(key).addVal(newTypeCount.get(key)/to.getTotalWords());
			}
		}
	}
	
	/**
	 * getter-method of this classes name
	 * 
	 * @return this TextClasses name as string
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * method for returning string-representation
	 * 
	 * @see java.lang.Object#toString()
	 * @return smallest, average and biggest text-size (number of words)
	 */
	public String toString(){
		String ret = "";
		ret += "VOCABULAR Values\n";
		ret += "--------------------------------\n";
		ret += this.textSizeMin + "\n";
		ret += this.getAverageTextSize() + "\n";
		ret += this.textSizeMax + "\n";
		return ret;
	}
}
