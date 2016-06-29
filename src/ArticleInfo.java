import java.util.Date;

/**
 * Created by donar on 16/6/8.
 */
public class ArticleInfo {
    String title;
    String author;
    String head;
    String tags;
    String status;
    String category;
    String summary;
    Date date;

    public String getTitle() {
        return title;
    }

    public ArticleInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleInfo setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getHead() {
        return head;
    }

    public ArticleInfo setHead(String head) {
        this.head = head;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public ArticleInfo setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ArticleInfo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ArticleInfo setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public ArticleInfo setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ArticleInfo setDate(Date date) {
        this.date = date;
        return this;
    }
}
