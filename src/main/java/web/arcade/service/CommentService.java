package web.arcade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.arcade.domain.Comment;
import web.arcade.repository.CommentRepository;
import web.arcade.domain.Profile;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private final ProfileService profileService;

    @Autowired
    public CommentService(CommentRepository commentRepository, ProfileService profileService) {
        this.commentRepository = commentRepository;
        this.profileService = profileService;
    }

    public List<Comment> findByContentId(Long contentId) {
        return commentRepository.findByCommentedContentId(contentId);
    }

    public List<Comment> findRecentComments(int limit) {
        return commentRepository.findRecentComments(limit);
    }

    public List<Comment> findByAuthorId(Long authorId) {
        return commentRepository.findByAuthorId(authorId);
    }
    
    public Comment createComment(Long profileId, Comment comment) throws Exception {
        Profile profile = profileService.findById(profileId);
        comment.setAuthor(profile);
        comment.setTimestamp(new Date()); // Data atual
        return commentRepository.save(comment);
    }
    
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    
    public void deleteCommentsByAuthor(Long authorId) {
        List<Comment> commentsToDelete = commentRepository.findByAuthorId(authorId);
        commentRepository.deleteAll(commentsToDelete);
    }
}