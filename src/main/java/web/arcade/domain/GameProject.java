package web.arcade.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class GameProject extends Content {
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "build_file_path")
    private String buildFilePath;

    @Column(name = "release_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date releaseDate;

    @Column(name = "is_recruiting")
    private Boolean isRecruiting;

    @ManyToMany
    @JoinTable(
            name = "project_applicants",
            joinColumns = @JoinColumn(name = "game_project_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> applicants;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Profile author;

    @OneToMany(mappedBy = "commentedContent")
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuildFilePath() {
        return buildFilePath;
    }

    public void setBuildFilePath(String buildFilePath) {
        this.buildFilePath = buildFilePath;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getRecruiting() {
        return isRecruiting;
    }

    public void setRecruiting(Boolean recruiting) {
        isRecruiting = recruiting;
    }

    public Set<Profile> getApplicants() {
        return applicants;
    }

    public void setApplicants(Set<Profile> applicants) {
        this.applicants = applicants;
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
