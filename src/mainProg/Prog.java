package mainProg;

import analyzer.*;

public class Prog {

	public static void main(String[] args) {
		Analyzer analyzer = new Analyzer();
		analyzer.init();
		analyzer.startTraining();
		
		for(TextClass TC : analyzer.getTextClasses().values()){
			System.out.println(TC.getName() + "\n");
			System.out.println("--------------------------------\n");
			System.out.println(TC);
		}
		
		analyzer.startGuessing();

	}
	
}
