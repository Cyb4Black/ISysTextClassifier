package analyzer;

import java.util.LinkedList;
import java.util.List;

public class DictType {
	private String Name;
	private List<String> content;
	
	public DictType(String n, LinkedList<String> in){
		Name = n;
		content = in;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}
}
