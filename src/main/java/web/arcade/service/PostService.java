package web.arcade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.arcade.domain.*;
import web.arcade.repository.PostRepository;
import web.arcade.repository.ProfileRepository;

import java.util.List;
import java.util.Set;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public PostService(PostRepository postRepository, ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public List<Post> findRecentlyCreated() {
        return postRepository.findRecentlyCreated();
    }

    public List<Post> findByTagId(Long tagId) {
        return postRepository.findByTagId(tagId);
    }

    public List<Post> findByAuthorId(Long profileId) {
        return postRepository.findByAuthorId(profileId);
    }

    public List<Comment> listAllComments(Long postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post with ID " + postId + " not found."));
        return post.getComments();
    }

    public Set<Tag> listAllTags(Long postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post with ID " + postId + " not found."));
        return post.getTags();
    }

    @Transactional
    public Post createPost(Post post, Long requestingProfileId) throws Exception {
        Profile profile = profileRepository.findById(requestingProfileId).orElseThrow(() -> new Exception("Profile with ID " + requestingProfileId + " not found."));
        post.setAuthor(profile);
        profile.getPosts().add(post);
        return postRepository.save(post);
    }

    @Transactional
    public void deletePostById(Long postId, Long requestingProfileId) throws Exception {
        Post postToDelete = postRepository.findById(postId).orElseThrow(() -> new Exception("Post with ID " + postId + " not found."));
        if (postToDelete.getAuthor().getProfileId().equals(requestingProfileId)) {
            Profile authorProfile = profileRepository.findById(postToDelete.getAuthor().getProfileId()).orElseThrow(() -> new Exception("Author profile not found"));
            authorProfile.getPosts().remove(postToDelete);
            profileRepository.save(authorProfile);
            postRepository.deleteById(postId);
        } else {
            throw new Exception("You can only delete your own posts.");
        }
    }

    @Transactional
    public Post updatePostContent(Long postId, String newContent, Long requestingProfileId) throws Exception {
        Post postToUpdate = postRepository.findById(postId).orElseThrow(() -> new Exception("Post with ID " + postId + " not found."));
        if (postToUpdate.getAuthor().getProfileId().equals(requestingProfileId)) {
            postToUpdate.setContent(newContent);
            return postRepository.save(postToUpdate);
        } else {
            throw new Exception("You can only update your own posts.");
        }
    }

    @Transactional
    public void addTagsToPost(Long postId, List<Tag> tagsToAdd, Long requestingProfileId) throws Exception {
        Post postToUpdate = postRepository.findById(postId).orElseThrow(() -> new Exception("Post with ID " + postId + " not found."));
        if (postToUpdate.getAuthor().getProfileId().equals(requestingProfileId)) {
            postToUpdate.getTags().addAll(tagsToAdd);
            postRepository.save(postToUpdate);
        } else {
            throw new Exception("You can only update your own game posts.");
        }
    }

    public void removeTagFromPost(Long postId, String tagName, Long requestingProfileId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post with ID " + postId + " not found."));
        if (post.getAuthor().getProfileId().equals(requestingProfileId)) {
            Set<Tag> projectTags = post.getTags();
            projectTags.removeIf(tag -> tag.getName().equals(tagName));
            postRepository.save(post);
        } else {
            throw new Exception("You can only modify tags for your own posts.");
        }
    }
}
