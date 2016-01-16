package analyzer;

import java.util.LinkedList;
import java.util.List;

/**
 * DictType-class, that holds the words to count per predefined word list (or e.g. signs)
 * 
 * @author Hex-3-En aka. Patrick Willnow & Marcel Selle
 * @version FINAL RELEASE
 *
 */
public class DictType {
	
	/**
	 * Name of a Type, defined by the Filename of the dict-source .txt
	 */
	private String Name;
	
	/**
	 * The list with the words (or signs) to count for this type
	 */
	private List<String> content;
	
	/**
	 * Constructor
	 * 
	 * @param n name of type
	 * @param in content of type
	 */
	public DictType(String n, LinkedList<String> in){
		Name = n;
		content = in;
	}

	/**
	 * getter-method for name
	 * 
	 * @return name of type as string
	 */
	public String getName() {
		return Name;
	}

	/**
	 * setter-method of type
	 * 
	 * @param name name to set for type as string
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * getter-method of content
	 * 
	 * @return content as list of strings
	 */
	public List<String> getContent() {
		return content;
	}

	/**
	 * setter method for content
	 * 
	 * @param content the content of type as list of strings
	 */
	public void setContent(List<String> content) {
		this.content = content;
	}
}
