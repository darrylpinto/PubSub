package TopicEvent;

import java.io.Serializable;

public class Event implements Serializable {
    private int id;
    private Topic topic;
    private String title;
    private String content;

    /* Constructor */
    public Event(int id, Topic topic, String title, String content) {
        this.id = id;
        this.topic = topic;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", topic=" + topic +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
