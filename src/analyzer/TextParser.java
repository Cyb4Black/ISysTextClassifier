package analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * TextParser that takes a text char by char and seperates it into words and signs
 * 
 * @author Hex-3-En aka. Patrick Willnow & Marcel Selle
 * @version FINAL RELEASE
 */
public class TextParser {
	
	/**
	 * Hard-coded String to represent signs to ignore when calculating wordcount
	 */
	private String punctuations = ".,;:?!»„-–()[]{}";
	
	/**
	 * Parsing method that runs through a file and generates a TextObject containing information about a text
	 * 
	 * @param f FileObject to analyze
	 * @return TextObject containing generated information about the text
	 */
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
					if(Character.isLetter(c)){
						dummy += c;//building word string to put into count-map
					}else if(dummy != ""){
						ret.addWord(dummy.toLowerCase());//add string to map
						dummy = "";
					}
					if(punctuations.indexOf(c) != -1){
						ret.addWord(c + ""); //if not char but punctuation, handle as a word
					}
					if(Character.isDigit(c)){
						dummy += c;//if is digit, handle like letter (digits and numers actually unused)
					}
				}
			}
			reader.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return ret;
	}
	
	
}
