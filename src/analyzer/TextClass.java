package analyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TextClass {
	private HashMap<String, Double> wordCount;
	private HashMap<String, Double> typeCount;
	private double vocabValSum;
	private double textSizeSum, textSizeMax, textSizeMin;
	private int textCount;
	private String name;
	private LinkedList<Map.Entry<String, Double>> topWords;
	public TextClass(String n){
		name = n;
		textCount = 0;
		vocabValSum = 0;
		textSizeSum = 0;
		textSizeMin = 99999;
		textSizeMax = 0;
		wordCount = new HashMap<String, Double>();
		typeCount = new HashMap<String, Double>();
	}
	public HashMap<String, Double> getAverageWordCount() {
		HashMap<String, Double> ret = new HashMap<String, Double>();
		for(String key : wordCount.keySet()){
			ret.put(key, (wordCount.get(key) / textCount));
		}
		return ret;
	}
	
	public HashMap<String, Double> getAverageTypeCount() {
		HashMap<String, Double> ret = new HashMap<String, Double>();
		for(String key : typeCount.keySet()){
			ret.put(key, (typeCount.get(key) / textCount));
		}
		return ret;
	}
	
	public double getAverageVocabVal(){
		return this.vocabValSum / textCount;
	}
	
	public double getAverageTextSize(){
		return this.textSizeSum / textCount;
	}
	
	public void addText(TextObject to){
		addWordCount(to.getWordCount());
		addTypeCount(to.getTypeCount());
		addVocabVal(to.getMyVocabVal());
		addTextSize(to.getTextSize());
		textCount++;
	}
	
	private void addTextSize(double size){
		textSizeSum += size;
		if(size > textSizeMax){
			textSizeMax = size;
		}else if(size < textSizeMin){
			textSizeMin = size;
		}
	}
	
	private void addVocabVal(double val){
		this.vocabValSum += val;
	}
	
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
	
	public void addTypeCount(HashMap<String, Double> newTypeCount) {
//		System.out.println(newTypeCount);
		for(String key : newTypeCount.keySet()){
			if(typeCount.containsKey(key)){
				double newVal = typeCount.get(key) + newTypeCount.get(key);
//				System.out.println(newVal);
				typeCount.put(key, newVal);
			}else{
				typeCount.put(key, newTypeCount.get(key));
			}
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		HashMap<String, Double> dummyMap;
		String ret = "";
		ret += "VOCABULAR Values\n";
		ret += "--------------------------------\n";
		ret += this.textSizeMin + "\n";
		ret += this.getAverageTextSize() + "\n";
		ret += this.textSizeMax + "\n";
		ret += this.getAverageVocabVal() + "\n";
//		ret += "WORDS AVERAGE\n";
//		ret += "--------------------------------\n";
//		dummyMap = this.getAverageWordCount();
//		for(String k : dummyMap.keySet()){
//			ret += k + ":\t\t" + dummyMap.get(k) + "\n";
//		}
		ret += "TYPES AVERAGE\n";
		ret += "--------------------------------\n";
		dummyMap = this.getAverageTypeCount();
		for(String k : dummyMap.keySet()){
			ret += k + ":\t\t" + dummyMap.get(k) + "\n";
		}
		return ret;
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
		LinkedList<String> blacklist = new LinkedList<String>();
		
		try {
			InputStream is = new FileInputStream("C:/Users/Hex-3-En/offlineWorkspaces/offline WS ISys/ISysTextClassifier/src/topWordsBlackList.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			String line = "";
			while(((line = reader.readLine()) != null)){
				blacklist.add(line);
			}
			reader.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Map.Entry<String, Double> ent : wordCount.entrySet()){
			if(!blacklist.contains(ent.getKey())){
				tempTop.add(ent);
			}
		}
		tempTop.sort(new WordMapComp());
		for(int i = 0; i < 10; i++){
			topWords.add(tempTop.get(i));
		}
	}
}
