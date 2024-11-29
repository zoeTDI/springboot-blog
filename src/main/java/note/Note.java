package note;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

/**
 * @author 18324
 */
public class Note {
    private String id = null;
    private String date;
    private List<String> tags;
    private String pic;
    private String category;
    private String title;
//    构造器
    public Note(String date, String pic, String category, String title, List<String> tags) {
        this.date = date;
        this.tags = tags;
        this.pic = pic;
        this.category = category;
        this.title = title;
        this.setId(date);
    }

//    装有默认数据的测试构造器
//    public Note() {
//        this.date = "20230501123456";
//        this.tags = List.of("Java", "SpringBoot");
//        this.pic = "https://cdn.jsdelivr.net/gh/Caldm/CDN@main/img/20230501171506.png";
//        this.category = "Java";
//        this.title = "Java SpringBoot";
//        this.setId(date);
//    }

    //    Getters and Setters
    public String getId() {
        return id;
    }
    private void setId(String id) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(id.getBytes());
            this.id = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
        if (this.id.isEmpty()) {
            this.setId(date);
        }
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return "Caldm";
    }

    @Override
    public String toString() {
        List<String> tags = this.getTags();
        String tagsString = String.join(", ", tags);
        return
"""
{
    id: %s,
    pic: %s,
    title: %s,
    category: %s,
    author: %s,
    date: %s,
    tags: [ %s ],
}
""".formatted(getId(), getPic(), getTitle(), getCategory(), getAuthor(), getDate(), tagsString);
    }
}
