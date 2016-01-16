package mainProg;

import analyzer.*;

/**
 * Main-class for running the program
 * 
 * @author Hex-3-En aka. Patrick Willnow & Marcel Selle
 * @version FINAL RELEASE
 *
 */
public class Prog {

	/**
	 * Main-method initializing analyzer and calling training and analyzing
	 * 
	 * @param args standard argument-array (unused)
	 */
	public static void main(String[] args) {
		Analyzer analyzer = new Analyzer();
		analyzer.init();
		analyzer.startTraining();
		//------------------------
		analyzer.startGuessing();

	}
	
}
