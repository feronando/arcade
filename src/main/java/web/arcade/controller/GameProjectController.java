package web.arcade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.arcade.domain.Comment;
import web.arcade.domain.GameProject;
import web.arcade.domain.Profile;
import web.arcade.domain.Tag;
import web.arcade.service.GameProjectService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/game-projects")
public class GameProjectController {
    private final GameProjectService gameProjectService;

    @Autowired
    public GameProjectController(GameProjectService gameProjectService) {
        this.gameProjectService = gameProjectService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GameProject>> getAllGameProjects() {
        return ResponseEntity.ok(gameProjectService.findAll());
    }

    @GetMapping("/{gameProjectId}")
    public ResponseEntity<GameProject> getGameProjectById(@PathVariable Long gameProjectId) {
        return ResponseEntity.ok(gameProjectService.findById(gameProjectId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<GameProject>> searchGameProjects(@RequestParam String title) {
        return ResponseEntity.ok(gameProjectService.findByTitleContaining(title));
    }

    @GetMapping("/recently-released")
    public ResponseEntity<List<GameProject>> getRecentlyReleasedProjects() {
        return ResponseEntity.ok(gameProjectService.findRecentlyReleased());
    }

    @GetMapping("/by-tag/{tagId}")
    public ResponseEntity<List<GameProject>> getProjectsByTagId(@PathVariable Long tagId) {
        return ResponseEntity.ok(gameProjectService.findByTagId(tagId));
    }

    @GetMapping("/by-author/{profileId}")
    public ResponseEntity<List<GameProject>> getProjectsByAuthorId(@PathVariable Long profileId) {
        return ResponseEntity.ok(gameProjectService.findByAuthorId(profileId));
    }

    @GetMapping("/recruiting")
    public ResponseEntity<List<GameProject>> getRecruitingProjects() {
        return ResponseEntity.ok(gameProjectService.findRecruitingProjects());
    }

    @GetMapping("/{gameProjectId}/applicants")
    public ResponseEntity getApplicantsForProject(@PathVariable Long gameProjectId){
        try {
            Set<Profile> applicants = gameProjectService.listApplicants(gameProjectId);
            return ResponseEntity.ok(applicants);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error recovering applicants: " + e.getMessage());
        }
    }

    @GetMapping("/{gameProjectId}/comments")
    public ResponseEntity getAllCommentsForProject(@PathVariable Long gameProjectId) {
        try {
            List<Comment> comments = gameProjectService.listAllComments(gameProjectId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error recovering comments: " + e.getMessage());
        }
    }

    @GetMapping("/{gameProjectId}/tags")
    public ResponseEntity getAllTagsForProject(@PathVariable Long gameProjectId){
        try {
            Set<Tag> tags = gameProjectService.listAllTags(gameProjectId);
            return ResponseEntity.ok(tags);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error recovering tags: " + e.getMessage());
        }
    }

    @PostMapping("/{requestingProfileId}/create")
    public ResponseEntity createGameProject(@RequestBody GameProject gameProject, @PathVariable Long requestingProfileId) {
        try {
            GameProject createdProject = gameProjectService.createGameProject(gameProject, requestingProfileId);
            return ResponseEntity.ok(createdProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating project: " + e.getMessage());
        }
    }

    @DeleteMapping("/{requestingProfileId}/{gameProjectId}")
    public ResponseEntity deleteGameProject(@PathVariable Long gameProjectId, @PathVariable Long requestingProfileId) {
        try {
            gameProjectService.deleteGameProjectById(gameProjectId, requestingProfileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting project: " + e.getMessage());
        }
    }

    @PutMapping("/{requestingProfileId}/{gameProjectId}")
    public ResponseEntity updateGameProject(@PathVariable Long gameProjectId, @RequestBody GameProject updatedProject, @PathVariable Long requestingProfileId) {
        try {
            GameProject updated = gameProjectService.updateGameProject(gameProjectId, updatedProject, requestingProfileId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating project: " + e.getMessage());
        }
    }

    @PutMapping("/tags/{requestingProfileId}/{gameProjectId}")
    public ResponseEntity addTagsToProject(@PathVariable Long gameProjectId, @RequestBody List<Tag> tagsToAdd, @PathVariable Long requestingProfileId) {
        try {
            gameProjectService.addTagsToGameProject(gameProjectId, tagsToAdd, requestingProfileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating project tags: " + e.getMessage());
        }
    }

    @DeleteMapping("/tags/{requestingProfileId}/{gameProjectId}/{tagName}")
    public ResponseEntity removeTagFromProject(@PathVariable Long gameProjectId, @PathVariable String tagName, @PathVariable Long requestingProfileId) {
        try {
            gameProjectService.removeTagFromProject(gameProjectId, tagName, requestingProfileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating project tags: " + e.getMessage());
        }
    }
}
