package web.arcade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.arcade.domain.Tag;
import web.arcade.service.TagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.findAll());
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long tagId) {
        Optional<Tag> optionalTag = Optional.of(tagService.findById(tagId));
        return optionalTag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {
        Optional<Tag> optionalTag = Optional.of(tagService.findByName(name));
        return optionalTag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity createTag(@RequestBody Tag tag, Long profileId) {
        if (tag.getName() == null || tag.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Tag name cannot be empty");
        }
        try {
            Tag newTag = tagService.saveTag(tag, profileId);
            return ResponseEntity.ok(newTag);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity deleteTag(@PathVariable Long tagId, @RequestParam Long profileId) {
        try {
            tagService.deleteTag(tagId, profileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }
    }

    @PutMapping("/{tagId}")
    public ResponseEntity updateTagName(@PathVariable Long tagId, @RequestParam String newTagName, Long profileId) {
        if (newTagName == null || newTagName.isEmpty()) {
            return ResponseEntity.badRequest().body("Tag name cannot be empty");
        }
        try {
            Tag updatedTag = tagService.updateTag(tagId, newTagName, profileId);
            if (updatedTag == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedTag);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }
    }
}
