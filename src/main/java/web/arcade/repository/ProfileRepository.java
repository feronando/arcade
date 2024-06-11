package web.arcade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.arcade.domain.Content;
import web.arcade.domain.Profile;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p FROM Profile p JOIN p.likedPosts l WHERE l.contentId = :postId")
    List<Profile> findUsersWhoLikedPost(@Param("postId") Long postId);

    @Query("SELECT p FROM Profile p JOIN p.likedGameProjects l WHERE l.contentId = :gameProjectId")
    List<Profile> findUsersWhoLikedGameProject(@Param("gameProjectId") Long gameProjectId);

    @Query("SELECT p FROM Profile p WHERE p.isAdmin = TRUE")
    List<Profile> listAdmins();

    @Query("SELECT po, g FROM Profile p JOIN p.connections c LEFT JOIN Post po ON (po.author = c) LEFT JOIN GameProject g ON (g.author = c) WHERE p.profileId = :profileId ORDER BY po.postDate DESC, g.releaseDate DESC")
    List<Content> findProfileFeed(@Param("profileId") Long profileId);
}
