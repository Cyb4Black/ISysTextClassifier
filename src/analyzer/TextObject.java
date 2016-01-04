package analyzer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TextObject {
	private HashMap<String, Double> wordCount;
	private HashMap<String, Double> typeCount;
//	private LinkedList<Map.Entry<String, Double>> topWords;
	private String name;
	private double myVocabVal;
	private double textSize;
	
	private String punctuations = ".,;:?!»„-";
	
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
	
	public double getTotalWords(){
		double ret = 0;
		for(String key : wordCount.keySet()){
			if(!punctuations.contains(key)){
				ret += wordCount.get(key);
			}
		}
		return ret;
	}
	
	public void addType(String dummy, double dummyVal) {
		if(typeCount.containsKey(dummy)){
			typeCount.put(dummy, typeCount.get(dummy) + dummyVal);
//			System.out.println("YES");
		}else{
			typeCount.put(dummy, dummyVal);
//			System.out.println("NO");
		}
	}

	public double getTextSize() {
		return textSize;
	}

	public void setTextSize(double textSize) {
		this.textSize = textSize;
	}

//	public LinkedList<Map.Entry<String, Double>> getTopWords() {
//		if(topWords == null){
//			setTopWords();
//		}
//		return topWords;
//	}
//
//	public void setTopWords() {
//		topWords = new LinkedList<Map.Entry<String, Double>>();
//		LinkedList<Map.Entry<String, Double>> tempTop = new LinkedList<Map.Entry<String, Double>>();
//		tempTop.addAll(this.wordCount.entrySet());
//		LinkedList<String> blacklist = new LinkedList<String>();
//		
//		try {
//			InputStream is = new FileInputStream("topWordsBlackList.txt");
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader reader = new BufferedReader(isr);
//			String line = "";
//			while(((line = reader.readLine()) != null)){
//				blacklist.add(line);
//			}
//			reader.close();
//			isr.close();
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		for(Map.Entry<String, Double> ent : tempTop){
//			if(blacklist.contains(ent.getKey())){
//				tempTop.remove(ent);
//			}
//		}
//		tempTop.sort(new WordMapComp());
//		for(int i = 0; i < 10; i++){
//			topWords.add(tempTop.get(i));
//		}
//	}
	

}
