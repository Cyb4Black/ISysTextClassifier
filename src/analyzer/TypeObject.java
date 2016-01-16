package analyzer;

import java.util.Collections;
import java.util.LinkedList;

/**
 * TypeObject-class holding information about one word-type (or dict-type)
 * to use for classification
 * 
 * @author Hex-3-En aka. Patrick Willnow & Marcel Selle
 * @version FINAL RELEASE
 *
 */
public class TypeObject {
	
	/**
	 * Name of Type taken from txt-file (actually unused)
	 */
	@SuppressWarnings("unused") //didn't know wheather it will be used later
	private String name;
	
	/**
	 * List of relative counts of this type per text learned
	 */
	private LinkedList<Double> vals;
	
	/**
	 * List of IntervalObjects containing information about generated intervals for this type
	 */
	private LinkedList<IntervalObject> intervals;
	
	/**
	 * Fields containing the smallest and biggest count in list for generating the intervals
	 */
	private double myMinVal, myMaxVal;

	/**
	 * Constructor initializing the the fields
	 * 
	 * @param n name of Type taken from File-name of .txt as String
	 */
	public TypeObject(String n) {
		this.myMinVal = 0;
		this.myMaxVal = 0;
		this.name = n;
		this.vals = new LinkedList<Double>();
		this.intervals = new LinkedList<IntervalObject>();
	}
	
	
	/**
	 * Method for creating the IntervalObjects fitting the calculated needs,
	 * also calls sub-method for calculating probabilities
	 */
	public void generateIntervals(){
		Collections.sort(vals);
		myMinVal = vals.getFirst();
		myMaxVal = vals.getLast();
		int ivCount = 3;
		double ivSize = (myMaxVal - myMinVal) / ivCount;
		for(int i = 0; i < ivCount; i++){
			intervals.add(new IntervalObject());
		}
		intervals.getFirst().min = myMinVal;
		intervals.getFirst().max = myMinVal + ivSize;
		for(int i = 1; i < ivCount; i++){
			intervals.get(i).min = intervals.get(i-1).max + Double.MIN_VALUE;
			intervals.get(i).max = intervals.get(i-1).max + ivSize;
		}
		intervals.getFirst().min = 0;//setting bottom-border of intervals to 0 for catching vals smaller than known from training
		intervals.getLast().max = Double.MAX_VALUE;//setting top-border of intervals to max for catching vals bigger than known from training
		genProb();
	}
	
	/**
	 * method for adding a single count while training-phase
	 * 
	 * @param in relative count to add as double
	 */
	public void addVal(double in){
		vals.add(in);
	}
	
	/**
	 * method for finding the probability in analyzing-phase
	 * 
	 * @param find relative count to find probability for as double
	 * @return found probability as double
	 */
	public double getProb(double find){
		double ret = 0;
		
		for(IntervalObject go : intervals){
			if(go.isIn(find))ret += go.prob; 
		}
		ret = (ret == 0) ? ret = 0.001 : ret;
		//giving back 0.001 as probability, if count lies in interval that didn't get a count in training

		return ret;
	}
	
	/**
	 * sub-method for calculating probabilities at the end of interval-generation.
	 * called by generateIntervals()-method
	 */
	private void genProb(){
		for(IntervalObject io : intervals){
			double count = 0;
			for(Double test : vals){
				if(io.isIn(test))count++;
			}
			io.prob = count/(vals.size() * 1.0);
		}
	}
}