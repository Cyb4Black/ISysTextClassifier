package analyzer;

import java.util.HashMap;

public class TextObject {
	private HashMap<String, Double> wordCount;
	private HashMap<String, Double> typeCount;
	private String name;
	
	public TextObject(String n){
		name = n;
		wordCount = new HashMap<String, Double>();
		typeCount = new HashMap<String, Double>();
	}

	public HashMap<String, Double> getWordCount() {
		return wordCount;
	}

	public void setWordCount(HashMap<String, Double> wordCount) {
		this.wordCount = wordCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Double> getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(HashMap<String, Double> typeCount) {
		this.typeCount = typeCount;
	}

	public void addWord(String dummy) {
		if(wordCount.containsKey(dummy)){
			wordCount.put(dummy, wordCount.get(dummy) + 1);
		}else{
			wordCount.put(dummy, 1.0);
		}
	}
	
	public void addType(String dummy) {
		if(typeCount.containsKey(dummy)){
			typeCount.put(dummy, typeCount.get(dummy) + 1);
		}else{
			typeCount.put(dummy, 1.0);
		}
	}

}
