package web.arcade;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import web.arcade.domain.Profile;

import java.util.Date;

@Entity
@Table(name = "connections", uniqueConstraints = @UniqueConstraint(columnNames = {"profile_id", "connected_profile_id"}))
public class Connection {
    @Id
    @Column(name = "connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long connectionId;

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "connected_profile_id")
    private Profile connectedProfile;
}
