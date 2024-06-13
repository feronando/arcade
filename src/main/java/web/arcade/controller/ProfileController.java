package web.arcade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.arcade.domain.ConnectProfileRequest;
import web.arcade.domain.Content;
import web.arcade.domain.GameProject;
import web.arcade.domain.Message;
import web.arcade.domain.Post;
import web.arcade.domain.Profile;
import web.arcade.domain.User;
import web.arcade.service.ProfileService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
	private final ProfileService profileService;
	
	@Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
	
	@GetMapping("/all")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok(profileService.findAll());
    }
	
	@GetMapping("/{profileID}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long profileID) {
		Optional<Profile> optionalTag = java.util.Optional.empty();
		try {
			optionalTag = Optional.of(profileService.findById(profileID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionalTag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
	
	@GetMapping("/admins")
    public ResponseEntity<List<Profile>> getAdminsProfiles() {
        return ResponseEntity.ok(profileService.listAdmins());
    }
	
	@GetMapping("/connections/{profileID}")
	public ResponseEntity<List<Profile>> getConnectionsById(@PathVariable Long profileID) {
	    try {
	        List<Profile> connections = profileService.findConnectionsByProfileId(profileID);
	        return ResponseEntity.ok(connections);
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/appliedProjects/{profileId}")
	public ResponseEntity<List<GameProject>> getAppliedProjects(@PathVariable Long profileId) {
	    try {
	        List<GameProject> appliedProjects = profileService.getAppliedProjects(profileId);
	        return ResponseEntity.ok(appliedProjects);
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping("/createdProjects/{profileId}")
	public ResponseEntity<List<GameProject>> getCreatedProjects(@PathVariable Long profileId) {
	    try {
	        List<GameProject> createdProjects = profileService.getCreatedProjects(profileId);
	        return ResponseEntity.ok(createdProjects);
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping("/createdPosts/{profileId}")
	public ResponseEntity<List<Post>> getCreatedPosts(@PathVariable Long profileId) {
	    try {
	        List<Post> createdPosts = profileService.getCreatedPosts(profileId);
	        return ResponseEntity.ok(createdPosts);
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping("/sentMessages/{profileId}")
	public ResponseEntity<List<Message>> getSentMessages(@PathVariable Long profileId) {
	    try {
	        List<Message> sentMessages = profileService.getSentMessages(profileId);
	        return ResponseEntity.ok(sentMessages);
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping("/receivedMessages/{profileId}")
	public ResponseEntity<List<Message>> getReceivedMessages(@PathVariable Long profileId) {
	    try {
	        List<Message> receivedMessages = profileService.getReceivedMessages(profileId);
	        return ResponseEntity.ok(receivedMessages);
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping("/connectProfiles")
	public ResponseEntity<Void> connectProfiles(@RequestBody ConnectProfileRequest request) {
	    Long profileId1 = request.getProfileId1AsLong();
	    Long profileId2 = request.getProfileId2AsLong();
	    try {
	        profileService.connectProfiles(profileId1, profileId2);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping("/profileFeed/{profileId}")
	public ResponseEntity<List<Content>> getProfileFeed(@PathVariable Long profileId) {
	    List<Content> feed = profileService.profileFeed(profileId);
	    return ResponseEntity.ok(feed);
	}

	@PostMapping("/likePost/{profileId}/{postId}")
	public ResponseEntity<Void> likePost(@PathVariable Long profileId, @PathVariable Long postId) {
	    try {
	        profileService.likePost(profileId, postId);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@DeleteMapping("/unlikePost/{profileId}/{postId}")
	public ResponseEntity<Void> unlikePost(@PathVariable Long profileId, @PathVariable Long postId) {
	    try {
	        profileService.unlikePost(profileId, postId);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@PostMapping("/likeGameProject/{profileId}/{gameProjectId}")
	public ResponseEntity<Void> likeGameProject(@PathVariable Long profileId, @PathVariable Long gameProjectId) {
	    try {
	        profileService.likeGameProject(profileId, gameProjectId);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@DeleteMapping("/unlikeGameProject/{profileId}/{gameProjectId}")
	public ResponseEntity<Void> unlikeGameProject(@PathVariable Long profileId, @PathVariable Long gameProjectId) {
	    try {
	        profileService.unlikeGameProject(profileId, gameProjectId);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@PostMapping("/favoriteProject/{profileId}/{gameProjectId}")
	public ResponseEntity<Void> favoriteProject(@PathVariable Long profileId, @PathVariable Long gameProjectId) {
	    try {
	        profileService.favoriteProject(profileId, gameProjectId);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@DeleteMapping("/unfavoriteProject/{profileId}/{gameProjectId}")
	public ResponseEntity<Void> unfavoriteProject(@PathVariable Long profileId, @PathVariable Long gameProjectId) {
	    try {
	        profileService.unfavoriteProject(profileId, gameProjectId);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@PostMapping("/applyToProject/{profileId}/{gameProjectId}")
	public ResponseEntity<Void> applyToProject(@PathVariable Long profileId, @PathVariable Long gameProjectId) {
	    try {
	        profileService.applyToProject(profileId, gameProjectId);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}

	@DeleteMapping("/removeApplication/{profileId}/{gameProjectId}")
	public ResponseEntity<Void> removeApplication(@PathVariable Long profileId, @PathVariable Long gameProjectId) {
	    try {
	        profileService.removeApplication(profileId, gameProjectId);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.notFound().build();
	    }
	}
    
    @PostMapping("/create/{userId}")
    public ResponseEntity<Profile> createProfile(@PathVariable Long userId,@RequestBody Profile profile) {
        try {
            Profile newProfile = profileService.createProfile(userId, profile);
            return ResponseEntity.ok(newProfile);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{profileToBeDeleted}")
    public ResponseEntity deleteProfile(@PathVariable Long profileToBeDeleted, @RequestParam Long profileId) {
        try {
            profileService.deleteProfile(profileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting profile: " + e.getMessage());
        }
    }

    @PutMapping("/{profileId}")
    public ResponseEntity updateProfile(@PathVariable Long profileId, @RequestBody Profile updatedProfile) throws Exception {
        try {
            Profile updated = profileService.updateProfile(updatedProfile);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating profile: " + e.getMessage());
        }
        }
}
