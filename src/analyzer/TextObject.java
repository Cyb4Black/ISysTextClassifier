package analyzer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TextObject {
	private HashMap<String, Double> wordCount;
	private HashMap<String, Double> typeCount;
	private LinkedList<Map.Entry<String, Double>> topWords;
	private String name;
	private double myVocabVal;
	private double textSize;
	
	public double getMyVocabVal() {
		return myVocabVal;
	}

	public void setMyVocabVal(double myVocabVal) {
		this.myVocabVal = myVocabVal;
	}

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

	public double getTextSize() {
		return textSize;
	}

	public void setTextSize(double textSize) {
		this.textSize = textSize;
	}

	public LinkedList<Map.Entry<String, Double>> getTopWords() {
		if(topWords == null){
			setTopWords();
		}
		return topWords;
	}

	public void setTopWords() {
		topWords = new LinkedList<Map.Entry<String, Double>>();
		LinkedList<Map.Entry<String, Double>> tempTop = new LinkedList<Map.Entry<String, Double>>();
		tempTop.addAll(this.wordCount.entrySet());
		tempTop.sort(new WordMapComp());
		for(int i = 0; i < 10; i++){
			topWords.add(tempTop.get(i));
		}
	}
	

}
