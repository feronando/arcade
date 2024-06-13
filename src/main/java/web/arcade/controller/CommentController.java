package web.arcade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.arcade.domain.Comment;
import web.arcade.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/content/{contentId}")
    public ResponseEntity<List<Comment>> findByContentId(@PathVariable Long contentId) {
        List<Comment> comments = commentService.findByContentId(contentId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/recent/{limit}")
    public ResponseEntity<List<Comment>> findRecentComments(@PathVariable int limit) {
        List<Comment> comments = commentService.findRecentComments(limit);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Comment>> findByAuthorId(@PathVariable Long authorId) {
        List<Comment> comments = commentService.findByAuthorId(authorId);
        return ResponseEntity.ok(comments);
    }
    
    @PostMapping("/create/{profileId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long profileId, @RequestBody Comment comment) {
        Comment newComment;
		try {
			newComment = commentService.createComment(profileId, comment);
			return ResponseEntity.ok(newComment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		}
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/deleteCommentsByAuthor/{authorId}")
    public ResponseEntity<Void> deleteCommentsByAuthor(@PathVariable Long authorId) {
        try {
            commentService.deleteCommentsByAuthor(authorId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
        
}
