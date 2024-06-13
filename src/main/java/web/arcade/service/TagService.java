package web.arcade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.arcade.domain.Profile;
import web.arcade.domain.Tag;
import web.arcade.repository.TagRepository;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final ProfileService profileService;

    @Autowired
    public TagService(TagRepository tagRepository, ProfileService profileService) {
        this.tagRepository = tagRepository;
        this.profileService = profileService;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById(Long tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    public Tag findByName(String name) {
        return tagRepository.findTagByName(name);
    }

    private boolean isAdmin(Long profileId) {
        Profile profile = new Profile();
		try {
			profile = profileService.findById(profileId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return profile != null && profile.getIsAdmin();
    }

    public Tag saveTag(Tag tag, Long profileId) throws Exception {
        if (!isAdmin(profileId)) {
            throw new Exception("Unauthorized to create tag. Admin access required.");
        }
        return tagRepository.save(tag);
    }

    public void deleteTag(Long tagId, Long profileId) throws Exception {
        if (!isAdmin(profileId)) {
            throw new Exception("Unauthorized to delete tag. Admin access required.");
        }
        tagRepository.deleteById(tagId);
    }

    public Tag updateTag(Long tagId, String newTagName, Long profileId) throws Exception {
        if (!isAdmin(profileId)) {
            throw new Exception("Unauthorized to update tag. Admin access required.");
        }
        Tag updatingTag = findById(tagId);
        if (updatingTag == null) {
            return null;
        }
        updatingTag.setName(newTagName);
        return tagRepository.save(updatingTag);
    }
}
