package web.arcade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.arcade.domain.Tag;
import web.arcade.domain.User;
import web.arcade.service.UserService;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        
    }

    @GetMapping("/all")//OK
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userId}")//OK
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> optionalTag = Optional.of(userService.findById(userId));
        return optionalTag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findByName/{name}")//OK
    public ResponseEntity<List<User>> getUserByName(@PathVariable String name) {
        List<User> users = userService.findByName(name);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findByEmail/{email}")//OK
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> optionalTag = Optional.of(userService.findByEmail(email));
        return optionalTag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findByUsername/{username}")//OK
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> optionalTag = Optional.of(userService.findByUsername(username));
        return optionalTag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findByNameContaining/{name}")//OK
    public ResponseEntity<List<User>> getUserByNameContaining(@PathVariable String name) {
        List<User> users = userService.findByNameContaining(name);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findByUsernameContaining/{username}")//OK
    public ResponseEntity<List<User>> getUserByUsernameContaining(@PathVariable String username) {
        List<User> users = userService.findByUsernameContaining(username);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")//OK
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        logger.info("Solicitação POST para obter todos os usuários foi chamada.");
        return ResponseEntity.ok(newUser);
        
    }

    @DeleteMapping("/{userToBeDeleted}")//OK
    public ResponseEntity deleteUser(@PathVariable Long userToBeDeleted, @RequestParam Long profileId) {
        try {
            userService.deleteUser(profileId, userToBeDeleted);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    @PutMapping("/{profileId}")//OK
    public ResponseEntity updateUser(@PathVariable Long profileId, @RequestBody User updatedUser) throws Exception {
        try {
            User updated = userService.updateUserByProfile(profileId, updatedUser);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }

}
