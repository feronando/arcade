package web.arcade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.arcade.domain.Comment;
import web.arcade.domain.GameProject;
import web.arcade.domain.Post;
import web.arcade.domain.Tag;
import web.arcade.service.PostService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/recently-created")
    public ResponseEntity<List<Post>> findRecentlyCreated() {
        return ResponseEntity.ok(postService.findRecentlyCreated());
    }

    @GetMapping("/by-tag/{tagId}")
    public ResponseEntity<List<Post>> getPostsByTagId(@PathVariable Long tagId) {
        return ResponseEntity.ok(postService.findByTagId(tagId));
    }

    @GetMapping("/by-author/{profileId}")
    public ResponseEntity<List<Post>> getPostsByAuthorId(@PathVariable Long profileId) {
        return ResponseEntity.ok(postService.findByAuthorId(profileId));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity getAllCommentsForPost(@PathVariable Long postId) {
        try {
            List<Comment> comments = postService.listAllComments(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error recovering comments: " + e.getMessage());
        }
    }

    @GetMapping("/{postId}/tags")
    public ResponseEntity getAllTagsForPost(@PathVariable Long postId){
        try {
            Set<Tag> tags = postService.listAllTags(postId);
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error recovering tags: " + e.getMessage());
        }
    }

    @PostMapping("/{requestingProfileId}/create")
    public ResponseEntity createPost(@RequestBody Post post, @PathVariable Long requestingProfileId) {
        try {
            Post createdProject = postService.createPost(post, requestingProfileId);
            return ResponseEntity.ok(createdProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating project: " + e.getMessage());
        }
    }

    @DeleteMapping("/{requestingProfileId}/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, @PathVariable Long requestingProfileId) {
        try {
            postService.deletePostById(postId, requestingProfileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting project: " + e.getMessage());
        }
    }

    @PutMapping("/{requestingProfileId}/{postId}")
    public ResponseEntity updatePostContent(@PathVariable Long postId, @RequestBody String content, @PathVariable Long requestingProfileId) {
        try {
            Post updated = postService.updatePostContent(postId, content, requestingProfileId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating project: " + e.getMessage());
        }
    }

    @PutMapping("/tags/{requestingProfileId}/{postId}")
    public ResponseEntity addTagsToPost(@PathVariable Long postId, @RequestBody List<Tag> tagsToAdd, @PathVariable Long requestingProfileId) {
        try {
            postService.addTagsToPost(postId, tagsToAdd, requestingProfileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating project tags: " + e.getMessage());
        }
    }

    @DeleteMapping("/tags/{requestingProfileId}/{postId}/{tagName}")
    public ResponseEntity removeTagFromPost(@PathVariable Long postId, @PathVariable String tagName, @PathVariable Long requestingProfileId) {
        try {
            postService.removeTagFromPost(postId, tagName, requestingProfileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating project tags: " + e.getMessage());
        }
    }
}
