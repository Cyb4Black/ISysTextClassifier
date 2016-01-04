package analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class TextParser {
	
	private String punctuations = ".,;:?!»„-";
//	private String alphaSpec = "äöüÄÖÜß";
	
	public TextObject parse(File f){
		TextObject ret = new TextObject(f.getName());
		
		try {
			InputStream is = new FileInputStream(f.getAbsoluteFile());
			InputStreamReader isr = new InputStreamReader(is, Charset.forName("Windows-1252"));
			BufferedReader reader = new BufferedReader(isr);
			String line = "";
			while(((line = reader.readLine()) != null)){
				String dummy = "";
				for(char c : line.toCharArray()){
					if(Character.isLetter(c) /*|| alphaSpec.indexOf(c) != -1*/){
						dummy += c;
					}else if(dummy != ""){
						ret.addWord(dummy);
						dummy = "";
					}
					if(punctuations.indexOf(c) != -1){
						ret.addWord(c + "");
					}
				}
			}
			reader.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		calcVocab(ret);
		return ret;
	}
	
	private void calcVocab(TextObject to){
		double totalWords = 0, vocabs = 0;
		for(String k : to.getWordCount().keySet()){
			if(k.length() > 1){
				vocabs++;
				totalWords += to.getWordCount().get(k);
			}
		}
		to.setTextSize(totalWords);
		to.setMyVocabVal(vocabs/totalWords);
	}
}
