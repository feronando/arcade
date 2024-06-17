package web.arcade.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "joined_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date joinedDate;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "connections",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "connected_profile_id"))
    private List<Profile> connections;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<GameProject> createdProjects;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

//    @OneToMany(mappedBy = "sharedBy")
//    private List<Share> sharedContent;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "liked_posts",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "content_id")
    )
    private List<Post> likedPosts;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "liked_game_projects",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "content_id")
    )
    private List<GameProject> likedGameProjects;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "profile_favorites",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "game_project_id")
    )
    private List<GameProject> favoritedProjects;

    @ManyToMany(mappedBy = "applicants")
    private Set<GameProject> appliedToProjects;

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Profile> getConnections() {
        return connections;
    }

    public void setConnections(List<Profile> connections) {
        this.connections = connections;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<GameProject> getCreatedProjects() {
        return createdProjects;
    }

    public void setCreatedProjects(List<GameProject> createdProjects) {
        this.createdProjects = createdProjects;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

//    public List<Share> getSharedContent() {
//        return sharedContent;
//    }
//
//    public void setSharedContent(List<Share> sharedContent) {
//        this.sharedContent = sharedContent;
//    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public List<GameProject> getLikedGameProjects() {
        return likedGameProjects;
    }

    public void setLikedGameProjects(List<GameProject> likedGameProjects) {
        this.likedGameProjects = likedGameProjects;
    }

    public List<GameProject> getFavoritedProjects() {
        return favoritedProjects;
    }

    public void setFavoritedProjects(List<GameProject> favoritedProjects) {
        this.favoritedProjects = favoritedProjects;
    }

    public Set<GameProject> getAppliedToProjects() {
        return appliedToProjects;
    }

    public void setAppliedToProjects(Set<GameProject> appliedToProjects) {
        this.appliedToProjects = appliedToProjects;
    }
}