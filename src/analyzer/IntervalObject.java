package analyzer;

/**
 * InvtervalObject-class that holds the interavl-borders and the probability
 * a relative count hits this interval
 * 
 * @author Hex-3-En aka. Patrick Willnow & Marcel Selle
 * @version FINAL RELEASE
 *
 */
public class IntervalObject {
	
	/**
	 * Minimal border of interval
	 */
	double min;
	
	/**
	 * Maximum-border of interval
	 */
	double max;
	
	/**
	 * probability for this interval
	 */
	double prob;
	
	/**
	 * Constructor
	 * initializes the 3 fields with 0
	 */
	public IntervalObject(){
		min = 0;
		max = 0;
		prob = 0;
	}
	
	/**
	 * method for finding out wheather a relative count hits this interval
	 * 
	 * @param test relative count to test
	 * @return true, if tested count in interval, else false
	 */
	public boolean isIn(Double test){
		if(min <= test && test <= max)return true;
		else return false;
	}
}
