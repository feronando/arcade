package web.arcade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.arcade.domain.Profile;
import web.arcade.domain.Tag;
import web.arcade.domain.User;
import web.arcade.repository.ProfileRepository;
import web.arcade.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService; // Inject ProfileService

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
            this.profileService = profileService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findByUsernameContaining(String username) {
        return userRepository.findByUsernameContaining(username);
    }

    public List<User>  findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findByNameContaining(String name) {
        return userRepository.findByNameContaining(name);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        Profile profile = new Profile();
        savedUser.setProfile(new Profile());
        return userRepository.save(savedUser);
    }

    @Transactional
    public void deleteUser(Long profileId, Long userId) throws Exception {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            if (profile.getUser().getUserId().equals(userId) || profile.getIsAdmin()) {
                userRepository.deleteById(userId);
                profileService.deleteProfile(profileId);
            } else {
                throw new Exception("You can only delete your own profile or profiles as admin.");
            }
        } else {
            throw new Exception("Profile with ID " + profileId + " not found.");
        }
    }

    @Transactional
    public User updateUserByProfile(Long profileId, User updatedUser) throws Exception {
        Optional<Profile> profileOptional = profileRepository.findById(profileId);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            User userToUpdate = profile.getUser();

            if (!userToUpdate.getUserId().equals(updatedUser.getUserId())) {
                throw new Exception("You can only update your own profile.");
            }
            userToUpdate.setName(updatedUser.getName());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setPassword(updatedUser.getPassword());
            userRepository.save(userToUpdate);
            return userToUpdate;
        } else {
            throw new Exception("Profile with ID " + profileId + " not found.");
        }
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
