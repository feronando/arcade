package web.arcade.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "shares")
public class Share {
    @Id
    @Column(name = "share_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shareId;

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content sharedContent;

    @ManyToOne
    @JoinColumn(name = "shared_by")
    private Profile sharedBy;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Content getSharedContent() {
        return sharedContent;
    }

    public void setSharedContent(Content sharedContent) {
        this.sharedContent = sharedContent;
    }

    public Profile getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(Profile sharedBy) {
        this.sharedBy = sharedBy;
    }
}
