import java.io.Serializable;
import java.util.List;

public class Topic implements Serializable{
	private int id;
	private List<String> keywords;
	private String name;

	public Topic(int id, String name){
		this.id = id;
		this.name = name;
	}

	public Topic(int id, String name, List<String> keywords){
		this.id = id;
		this.name = name;
		this.keywords = keywords;
	}
	
	public void addKeyword(String keyword){
		//Adding a keyword to the topic
		this.keywords.add(keyword);
	}
}
