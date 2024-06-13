package web.arcade.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Post extends Content {
    @Column(name = "content")
    private String content;

    @Column(name = "post_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date postDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Profile author;

    @OneToMany(mappedBy = "commentedContent")
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
