package analyzer;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Analyzer {
	private String dictPath = "dicts/";
	private String sourcePath = "textSources/";
	private String trainingPath = "Training/";
	private String guessingPath = "Test/";
	
	private List<DictType> wordTypes;
	private HashMap<String, TextClass> textClasses;
	private TextParser parser;
	
	FilenameFilter textFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.endsWith(".txt")) {
				return true;
			} else {
				return false;
			}
		}
	};
	
	public Analyzer(){
		setTokenTypes(new LinkedList<DictType>());
		setTextClasses(new HashMap<String, TextClass>());
		parser = new TextParser();
	}
	
	public void learnText(File f, String tClass){
		TextObject in = parser.parse(f);
		analyzeTypes(in);
		textClasses.get(tClass).addText(in);
	}
	
	private void analyzeTypes(TextObject in) {
		for(String w : in.getWordCount().keySet()){
			for(DictType dt : wordTypes){
				if(dt.getContent().contains(w)){
					in.addType(dt.getName());
					break;
				}
			}
		}
	}
	
	public boolean analyzeText(File f, String tClass){
		TextObject in = parser.parse(f);
		analyzeTypes(in);
		String result = guessText(in);
		
		if(result.equals(tClass)){
			System.out.println(tClass + " : " + result + "\tYEAH, got it RIGHT");
			return true;
		}else{
			System.out.println(tClass + " : " + result + "\tDAMN!!!, got it WRONG");
			return false;
		}
	}
	
	private String guessText(TextObject in) {
		String tempRet = "";
		double minDiff = 100;
		for(TextClass TC : this.textClasses.values()){
			double tempDiff = 0;
			for(String type : in.getTypeCount().keySet()){
				tempDiff += ((100/TC.getAverageTypeCount().get(type)) * 
						Math.abs(TC.getAverageTypeCount().get(type) - 
								in.getTypeCount().get(type)));
			}
			tempDiff /= in.getTypeCount().keySet().size();
			if(tempDiff < minDiff){
				minDiff = tempDiff;
				tempRet = TC.getName();
			}
		}
		
		return tempRet;
	}

	public void init(){
		File dictDir = new File(dictPath);
		
		for(File f : dictDir.listFiles(textFilter)){
//			System.out.println(f.getAbsolutePath());
			wordTypes.add(new DictType(f.getName(), parseType(f)));
		}
		
		File sourceDir = new File(sourcePath);
		for(File f : sourceDir.listFiles()){
//			System.out.println(f.getAbsolutePath());
			textClasses.put(f.getName(), new TextClass(f.getName()));
		}
		
	}
	
	private LinkedList<String> parseType(File f){
		LinkedList<String> ret = new LinkedList<String>();
		
		try {
			InputStream is = new FileInputStream(f.getAbsoluteFile());
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			String line = "";
			while(((line = reader.readLine()) != null)){
				ret.add(line);
//				System.out.println(line);
			}
			reader.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return ret;
	}

	public List<DictType> getTokenTypes() {
		return wordTypes;
	}

	public void setTokenTypes(List<DictType> tokenTypes) {
		this.wordTypes = tokenTypes;
	}

	public HashMap<String, TextClass> getTextClasses() {
		return textClasses;
	}

	public void setTextClasses(HashMap<String, TextClass> textClasses) {
		this.textClasses = textClasses;
	}

	public void startTraining() {
		for(TextClass TC : this.textClasses.values()){
			File tPath = new File(sourcePath + TC.getName() + "/" + trainingPath);
			for(File f : tPath.listFiles(textFilter)){
				learnText(f, TC.getName());
			}
		}
	}
	
	public void startGuessing(){
		HashMap<String, Double> results = new HashMap<String, Double>();
		double ct = 0, rt = 0;//count total & guessed right total
		for(TextClass TC : this.textClasses.values()){
			double c = 0, r = 0;//count per class & guessed right per class
			File tPath = new File(sourcePath + TC.getName() + "/" + guessingPath);
			for(File f : tPath.listFiles(textFilter)){
				if(analyzeText(f, TC.getName())){
					c++;
					r++;
				}else{
					c++;
				}
			}
			ct += c;
			rt += r;
			results.put(TC.getName(), (r/c)*100.0);
		}
		String resMessage = "My guessing-results:\n";
		resMessage += "--------------------------------\n";
		for(String k : results.keySet()){
			resMessage += k + ":\t" + results.get(k) + "%\n";
		}
		resMessage += "Overall Quote:\t" + (rt/ct)*100 + "%";
		System.out.println(resMessage);
	}
}
