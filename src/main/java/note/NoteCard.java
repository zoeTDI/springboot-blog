package note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 18324
 */
public class NoteCard {
    private int id;
    private String title;
    private String pic;
    private String category;
    private String summary;
    private String date;
    private String author;
    private ArrayList<String> tags;
    private Map<String, Object> link;

    public NoteCard() {
        tags = new ArrayList<>();
        link = new HashMap<>();
        link.put("name", "Test");
        Map<String, Object> params = new HashMap<>();
        link.put("params", params);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    public void addTags(String tag) {
        if (tag != null && !tag.isEmpty()) {
            tags.add(tag);
        }
    }
    public Map<String, Object> getLink() {
        return this.link;
    }
}
