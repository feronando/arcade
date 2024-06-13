package web.arcade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.arcade.domain.*;
import web.arcade.repository.GameProjectRepository;
import web.arcade.repository.PostRepository;
import web.arcade.repository.ProfileRepository;
import web.arcade.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final GameProjectRepository gameProjectRepository;
    private final UserService userService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, PostRepository postRepository, GameProjectRepository gameProjectRepository, UserService userService) {
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
        this.gameProjectRepository = gameProjectRepository;
        this.userService = userService;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Profile findById(Long profileId) throws Exception {
        return profileRepository.findById(profileId).orElseThrow(() -> new Exception("Profile with ID " + profileId + " not found."));
    }

    public List<Profile> listAdmins() {
        return profileRepository.listAdmins();
    }

    public List<Profile> findConnectionsByProfileId(Long profileId) throws Exception {
        return findById(profileId).getConnections();
    }

    public List<GameProject> getAppliedProjects(Long profileId) throws Exception {
        Profile profile = findById(profileId);
        return new ArrayList<>(profile.getAppliedToProjects());
    }

    public List<GameProject> getCreatedProjects(Long profileId) throws Exception {
        Profile profile = findById(profileId);
        return new ArrayList<>(profile.getCreatedProjects());
    }

    public List<Post> getCreatedPosts(Long profileId) throws Exception {
        Profile profile = findById(profileId);
        return new ArrayList<>(profile.getPosts());
    }

    public List<Message> getSentMessages(Long profileId) throws Exception {
        Profile profile = findById(profileId);
        return new ArrayList<>(profile.getSentMessages());
    }

    public List<Message> getReceivedMessages(Long profileId) throws Exception {
        Profile profile = findById(profileId);
        return new ArrayList<>(profile.getReceivedMessages());
    }

    public void connectProfiles(Long profileId1, Long profileId2) throws Exception {
        Profile profile1 = findById(profileId1);
        Profile profile2 = findById(profileId2);

        if (!profile1.getConnections().contains(profile2)) {
            profile1.getConnections().add(profile2);
            profileRepository.save(profile1);
        }

        if (!profile2.getConnections().contains(profile1)) {
            profile2.getConnections().add(profile1);
            profileRepository.save(profile2);
        }
    }

    public List<Content> profileFeed(Long profileId) {
        return profileRepository.findProfileFeed(profileId);
    }

    public void likePost(Long profileId, Long postId) throws Exception {
        Profile profile = findById(profileId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post with ID " + postId + " not found."));
        if (!profile.getLikedPosts().contains(post)) {
            profile.getLikedPosts().add(post);
            profileRepository.save(profile);
        }
    }

    public void unlikePost(Long profileId, Long postId) throws Exception {
        Profile profile = findById(profileId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception("Post with ID " + postId + " not found."));
        profile.getLikedPosts().remove(post);
        profileRepository.save(profile);
    }

    public void likeGameProject(Long profileId, Long gameProjectId) throws Exception {
        Profile profile = findById(profileId);
        GameProject gameProject = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Project with ID " + gameProjectId + " not found."));
        if (!profile.getLikedGameProjects().contains(gameProject)) {
            profile.getLikedGameProjects().add(gameProject);
            profileRepository.save(profile);
        }
    }

    public void unlikeGameProject(Long profileId, Long gameProjectId) throws Exception {
        Profile profile = findById(profileId);
        GameProject gameProject = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Project with ID " + gameProjectId + " not found."));
        profile.getLikedGameProjects().remove(gameProject);
        profileRepository.save(profile);
    }

    public void favoriteProject(Long profileId, Long gameProjectId) throws Exception {
        Profile profile = findById(profileId);
        GameProject gameProject = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Project with ID " + gameProjectId + " not found."));
        if (!profile.getFavoritedProjects().contains(gameProject)) {
            profile.getFavoritedProjects().add(gameProject);
            profileRepository.save(profile);
        }
    }

    public void unfavoriteProject(Long profileId, Long gameProjectId) throws Exception {
        Profile profile = findById(profileId);
        GameProject gameProject = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Project with ID " + gameProjectId + " not found."));
        profile.getFavoritedProjects().remove(gameProject);
        profileRepository.save(profile);
    }

    public void applyToProject(Long profileId, Long gameProjectId) throws Exception {
        Profile profile = findById(profileId);
        GameProject project = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Project with ID " + gameProjectId + " not found."));
        if (!profile.getAppliedToProjects().contains(project)) {
            profile.getAppliedToProjects().add(project);
            profileRepository.save(profile);
        }
    }

    public void removeApplication(Long profileId, Long gameProjectId) throws Exception {
        Profile profile = findById(profileId);
        GameProject project = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Project with ID " + gameProjectId + " not found."));
        profile.getAppliedToProjects().remove(project);
        profileRepository.save(profile);
    }

    public Profile createProfile(Long userId, Profile newprofile) throws Exception {
        User user = userService.findById(userId);
        if (user.getProfile() != null) {
            throw new Exception("User with ID " + userId + " already has a profile.");
        }
        Profile profile = new Profile();
        profile = newprofile;
        profile.setUser(user);
        profileRepository.save(profile);
        user.setProfile(profile);
        userService.saveUser(user);
        return profile;
    }

    public Profile updateProfile(Profile profile) throws Exception {
        Profile existingProfile = findById(profile.getProfileId());
        existingProfile.setBio(profile.getBio());
        existingProfile.setProfilePicture(profile.getProfilePicture());
        return profileRepository.save(existingProfile);
    }

    public void deleteProfile(Long profileId) throws Exception {
        Profile profile = findById(profileId);

        for (Profile connection : profile.getConnections()) {
            connection.getConnections().remove(profile);
            profileRepository.save(connection);
        }

        for (Post post : profile.getPosts()) {
            postRepository.delete(post);
        }
        for (GameProject project : profile.getCreatedProjects()) {
            gameProjectRepository.delete(project);
        }

        profile.getLikedPosts().clear();
        profile.getLikedGameProjects().clear();
        profile.getFavoritedProjects().clear();
        profile.getAppliedToProjects().clear();

        userService.deleteUser(profileId, profile.getUser().getUserId());

        profileRepository.delete(profile);
    }
}
