package web.arcade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.arcade.domain.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY p.postDate DESC")
    List<Post> findRecentlyCreated();

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.tagId = :tagId")
    List<Post> findByTagId(@Param("tagId") Long tagId);

    @Query("SELECT p FROM Post p WHERE p.author.profileId = :profileId")
    List<Post> findByAuthorId(@Param("profileId") Long profileId);
}