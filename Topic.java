import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topic implements Serializable{
	private int id;
	private List<String> keywords;
	private String name;

	public Topic(int id, String name){
		this.id = id;
		this.name = name;
		this.keywords = new ArrayList<String>();
	}

	public Topic(int id, String name, List<String> keywords){
		this.id = id;
		this.name = name;
		this.keywords = keywords;
	}

	@Override
	public boolean equals(Object obj){
		Topic t = (Topic) obj;
		return this.name.equals(t.getName());

	}
	
	public void addKeyword(String keyword){
		//Adding a keyword to the topic
		this.keywords.add(keyword);
	}

	public String getName() {
		return name;
	}



}
