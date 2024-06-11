package web.arcade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.arcade.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.commentedContent.contentId = :contentId")
    List<Comment> findByCommentedContentId(@Param("contentId") Long contentId);

    @Query("SELECT c FROM Comment c ORDER BY c.timestamp DESC")
    List<Comment> findRecentComments(int limit);

    @Query("SELECT c FROM Comment c WHERE c.author.profileId = :authorId")
    List<Comment> findByAuthorId(@Param("authorId") Long authorId);
}

