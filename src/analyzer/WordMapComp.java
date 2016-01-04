package analyzer;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class WordMapComp implements Comparator<Map.Entry<String,Double>> {

	@Override
	public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
		if(o1.getValue() < o2.getValue()){
			return 1;
		}else if(o1.getValue() > o2.getValue()){
			return -1;
		}else{
		return 0;
		}
	}

}
