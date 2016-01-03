package analyzer;

import java.util.HashMap;

public class TextClass {
	private HashMap<String, Double> wordCount;
	private HashMap<String, Double> typeCount;
	private int textCount;
	private String name;
	public TextClass(String n){
		name = n;
		textCount = 0;
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
	
	public void addText(TextObject to){
		addWordCount(to.getWordCount());
		addTypeCount(to.getTypeCount());
		textCount++;
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
		for(String key : newTypeCount.keySet()){
			if(typeCount.containsKey(key)){
				double newVal = typeCount.get(key) + newTypeCount.get(key);
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
		ret += "VOKABULAR\n";
		ret += "--------------------------------\n";
		ret += this.wordCount.keySet().size() + "\n";
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
}
