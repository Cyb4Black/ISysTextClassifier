package analyzer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Analyzer Class
 * Main class for the classificator, which contains initial methods for learning
 * and  analyzing texts.
 * Also holds the of word-types (or dict-types) to use for guessing
 * and the known text-classes (in case of the ISys-project usually "Filme" and "Nachrichten".
 * 
 * @author Hex-3-En aka. Patrick Willnow & Marcel Selle
 * @version FINAL RELEASE
 */
public class Analyzer {
	/**
	 * Path to files that hold information about words to count for a word-type (or dict-type)
	 */
	private String dictPath = "dicts/";
	
	/**
	 * Path to the text-classes
	 */
	private String sourcePath = "textSources/";
	
	/**
	 * Sub-path to the texts used for learning phase
	 * (can in actual git-source be Training, Test, Test2, Evaluation)
	 */
	private String trainingPath = "Training/";
	
	/**
	 * Sub-path to the texts to analyze after learning
	 * (can in actual git-source be Training, Test, Test2, Evaluation)
	 */
	private String guessingPath = "Evaluation/";
	
	/**
	 * List that holds the word-types to use for analyzing 
	 */
	private LinkedList<DictType> wordTypes;
	
	/**
	 * Map that holds the known text-classes
	 * @key Name of text-class
	 * @value TextClass-object for each text-class
	 */
	private HashMap<String, TextClass> textClasses;
	
	/**
	 * Reference to TextParser used for reading text-files at learning-phase and analyzing-phase 
	 */
	private TextParser parser;

	/**
	 * Defining Filename-Filter for ignoring other files than .txt e.g. thumbs.db
	 */
	FilenameFilter textFilter = new FilenameFilter() {
		/**
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.endsWith(".txt")) {
				return true;
			} else {
				return false;
			}
		}
	};
	
	/**
	 * Constructor for Analyzer-Class
	 */
	public Analyzer() {
		wordTypes = new LinkedList<DictType>();
		textClasses = new HashMap<String, TextClass>();
		parser = new TextParser();
	}

	/**
	 * Method for calling used sub-methods used for learning a text
	 * 
	 * @param f text-file to learn text from
	 * @param tClass name of text-class, the text-file belongs to
	 */
	public void learnText(File f, String tClass) {
		TextObject in = parser.parse(f);
		analyzeTypes(in);
		textClasses.get(tClass).addText(in);
	}

	/**
	 * Sub-method of learning-process for getting the type-count for a text 
	 * 
	 * @param in incoming text-object of text to learn
	 */
	private void analyzeTypes(TextObject in) {
		for (String w : in.getWordCount().keySet()) {
			for (DictType dt : wordTypes) {
				if (dt.getContent().contains(w)) {
					in.addType(dt.getName(), in.getWordCount().get(w));
				}
			}
		}
	}

	/**
	 * top-method calling a single text-guessing and deciding wheather guessing result is right or wrong
	 * 
	 * @param f File-object of text to guess the class
	 * @param tClass real text-class for comparing with guessing-result
	 * @return true if text-class guessed right, else false
	 */
	public boolean analyzeText(File f, String tClass) {
		TextObject in = parser.parse(f);
		analyzeTypes(in);
		String result = guessTextBayes(in);

		if (result.equals(tClass)) {
			System.out
					.println(tClass + " : " + result + "\tYEAH, got it RIGHT");
			return true;
		} else {
			System.out.println(tClass + " : " + result
					+ "\tDAMN!!!, got it WRONG");
			return false;
		}
	}
	
	/**
	 * Method that calculates the probability that a text-file belongs to a text-class
	 * using the bayes-classification as introduced in ISys-lecture
	 * 
	 * @param in Text-object to guess the text-class for
	 * @return name of most probable text-class for guessed text
	 */
	private String guessTextBayes(TextObject in){
		LinkedList<Double> vals = new LinkedList<Double>();
		LinkedList<String> names = new LinkedList<String>();
		for(String s : this.textClasses.keySet()){
			double tempVal = 1;
			for(DictType dt : this.wordTypes){
				if(textClasses.get(s).getTypeCount().get(dt.getName()) == null || in.getTypeCount().get(dt.getName()) == null){
					continue;
				}
				double inVal = in.getTypeCount().get(dt.getName());
				double dummy = textClasses.get(s).getTypeCount().get(dt.getName()).getProb(inVal/in.getTotalWords());
				tempVal *= dummy;
			}
			vals.add(tempVal);
			names.add(s);
		}
		double max = Collections.max(vals);
		return names.get(vals.indexOf(max));
	}

	/**
	 * Method for initializing the analyzer-class with word-types and text-classes found in specified directorys.
	 * Has to be called at startup
	 */
	public void init() {
		File dictDir = new File(dictPath);
		String tempName = "";
		for (File f : dictDir.listFiles(textFilter)) {
			tempName = f.getName();
			tempName = tempName.substring(0, tempName.length() - 4);
			wordTypes.add(new DictType(tempName, parseType(f)));
		}

		File sourceDir = new File(sourcePath);
		for (File f : sourceDir.listFiles()) {
			textClasses.put(f.getName(), new TextClass(f.getName(), wordTypes));
		}

	}

	/**
	 * sub-method of init-process, that analyzes a txt-file as a word-type (or dict-type)
	 * 
	 * @param f File-object to get data for type from
	 * @return LinkedList of words (or tokens) specified in txt, that will be used for type-counting
	 */
	private LinkedList<String> parseType(File f) {
		LinkedList<String> ret = new LinkedList<String>();

		try {
			InputStream is = new FileInputStream(f.getAbsoluteFile());
			InputStreamReader isr = new InputStreamReader(is, Charset.forName("Windows-1252"));
			BufferedReader reader = new BufferedReader(isr);
			String line = "";
			while (((line = reader.readLine()) != null)) {
				ret.add(line);
			}
			reader.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * Getter-method of wordTypes
	 * @return this wordTypes
	 */
	public List<DictType> getTokenTypes() {
		return wordTypes;
	}

	/**
	 * Getter-method of TextClasses
	 * @return this textClasses
	 */
	public HashMap<String, TextClass> getTextClasses() {
		return textClasses;
	}

	/**
	 * initial method for learning-process
	 * that calls the needed sub-methods for each text found in specified directory
	 */
	public void startTraining() {

			for (TextClass TC : this.textClasses.values()) {
				File tPath = new File(sourcePath + TC.getName() + "/"
						+ trainingPath);
				for (File f : tPath.listFiles(textFilter)) {
					learnText(f, TC.getName());
				}
				for(TypeObject to : TC.getTypeCount().values()){
					to.generateIntervals();
				}
			}
	}

	/**
	 * initial method for analyzing-prozess
	 * that calls the needed sub-methods for each text found in specified directory,
	 * counts the correct answers and prints the quessing quote
	 */
	public void startGuessing() {
		HashMap<String, Double> results = new HashMap<String, Double>();
		double ct = 0, rt = 0;// count total & guessed right total
		for (TextClass TC : this.textClasses.values()) {
			double c = 0, r = 0;// count per class & guessed right per class
			File tPath = new File(sourcePath + TC.getName() + "/"
					+ guessingPath);
			for (File f : tPath.listFiles(textFilter)) {
				if (analyzeText(f, TC.getName())) {
					c++;
					r++;
				} else {
					c++;
				}
			}
			ct += c;
			rt += r;
			results.put(TC.getName(), (r / c) * 100.0);
		}
		String resMessage = "My guessing-results:\n";
		resMessage += "--------------------------------\n";
		for (String k : results.keySet()) {
			resMessage += k + ":\t" + results.get(k) + "%\n";
		}
		resMessage += "Overall Quote:\t" + (rt / ct) * 100 + "%";
		System.out.println(resMessage);
	}
}
