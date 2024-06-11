package web.arcade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.arcade.domain.Comment;
import web.arcade.domain.GameProject;
import web.arcade.domain.Profile;
import web.arcade.domain.Tag;
import web.arcade.repository.GameProjectRepository;
import web.arcade.repository.ProfileRepository;

import java.util.List;
import java.util.Set;

@Service
public class GameProjectService {
    private final GameProjectRepository gameProjectRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public GameProjectService(GameProjectRepository gameProjectRepository, ProfileRepository profileRepository) {
        this.gameProjectRepository = gameProjectRepository;
        this.profileRepository = profileRepository;
    }

    public List<GameProject> findAll() {
        return gameProjectRepository.findAll();
    }

    public GameProject findById(Long gameProjectId) {
        return gameProjectRepository.findById(gameProjectId).orElse(null);
    }

    public List<GameProject> findByTitleContaining(String title) {
        return gameProjectRepository.findByTitleContaining(title);
    }

    public List<GameProject> findRecentlyReleased() {
        return gameProjectRepository.findRecentlyReleased();
    }

    public List<GameProject> findByTagId(Long tagId) {
        return gameProjectRepository.findByTagId(tagId);
    }

    public List<GameProject> findByAuthorId(Long profileId) {
        return gameProjectRepository.findByAuthorId(profileId);
    }

    public List<GameProject> findRecruitingProjects() {
        return gameProjectRepository.findRecruitingProjects();
    }

    public Set<Profile> listApplicants(Long gameProjectId) throws Exception {
        GameProject project = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Game project with ID " + gameProjectId + " not found."));
        return project.getApplicants();
    }

    public List<Comment> listAllComments(Long gameProjectId) throws Exception {
        GameProject project = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Game project with ID " + gameProjectId + " not found."));
        return project.getComments();
    }

    public Set<Tag> listAllTags(Long gameProjectId) throws Exception {
        GameProject project = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Game project with ID " + gameProjectId + " not found."));
        return project.getTags();
    }

    @Transactional
    public GameProject createGameProject(GameProject gameProject, Long requestingProfileId) throws Exception {
        Profile profile = profileRepository.findById(requestingProfileId).orElseThrow(() -> new Exception("Profile with ID " + requestingProfileId + " not found."));
        gameProject.setAuthor(profile);
        profile.getCreatedProjects().add(gameProject);
        return gameProjectRepository.save(gameProject);
    }

    @Transactional
    public void deleteGameProjectById(Long gameProjectId, Long requestingProfileId) throws Exception {
        GameProject projectToDelete = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Game project with ID " + gameProjectId + " not found."));
        if (projectToDelete.getAuthor().getProfileId().equals(requestingProfileId)) {
            Profile authorProfile = profileRepository.findById(projectToDelete.getAuthor().getProfileId()).orElseThrow(() -> new Exception("Author profile not found"));
            authorProfile.getCreatedProjects().remove(projectToDelete);
            profileRepository.save(authorProfile);
            gameProjectRepository.deleteById(gameProjectId);
        } else {
            throw new Exception("You can only delete your own projects.");
        }
    }

    @Transactional
    public GameProject updateGameProject(Long gameProjectId, GameProject updatedProject, Long requestingProfileId) throws Exception {
        GameProject projectToUpdate = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Game project with ID " + gameProjectId + " not found."));
        if (projectToUpdate.getAuthor().getProfileId().equals(requestingProfileId)) {
            projectToUpdate.setTitle(updatedProject.getTitle());
            projectToUpdate.setDescription(updatedProject.getDescription());
            projectToUpdate.setBuildFilePath(updatedProject.getBuildFilePath());
            projectToUpdate.setReleaseDate(updatedProject.getReleaseDate());
            projectToUpdate.setRecruiting(updatedProject.getRecruiting());
            return gameProjectRepository.save(projectToUpdate);
        } else {
            throw new Exception("You can only update your own game projects.");
        }
    }

    @Transactional
    public void addTagsToGameProject(Long gameProjectId, List<Tag> tagsToAdd, Long requestingProfileId) throws Exception {
        GameProject projectToUpdate = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Game project with ID " + gameProjectId + " not found."));
        if (projectToUpdate.getAuthor().getProfileId().equals(requestingProfileId)) {
            projectToUpdate.getTags().addAll(tagsToAdd);
            gameProjectRepository.save(projectToUpdate);
        } else {
            throw new Exception("You can only update your own game projects.");
        }
    }

    public void removeTagFromProject(Long gameProjectId, String tagName, Long requestingProfileId) throws Exception {
        GameProject project = gameProjectRepository.findById(gameProjectId).orElseThrow(() -> new Exception("Game project with ID " + gameProjectId + " not found."));
        if (project.getAuthor().getProfileId().equals(requestingProfileId)) {
            Set<Tag> projectTags = project.getTags();
            projectTags.removeIf(tag -> tag.getName().equals(tagName));
            gameProjectRepository.save(project);
        } else {
            throw new Exception("You can only modify tags for your own projects.");
        }
    }
}
