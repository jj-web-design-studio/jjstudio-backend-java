package com.jjstudio.controller.me;

import com.jjstudio.dto.user.UpdateUserRequest;
import com.jjstudio.model.User;
import com.jjstudio.resource.UserRepository;
import com.jjstudio.util.Role;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/me/users")
@CrossOrigin("http://localhost:3000")
public class MyUserController {

    private final Logger logger = LoggerFactory.getLogger(MyUserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Get current user", notes = "${MyUserController.getCurrentUser.notes}")
    @GetMapping
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Update current user", notes = "${MyUserController.updateCurrentUser.notes}")
    @PostMapping
    public ResponseEntity<String> updateCurrentUser(@RequestBody UpdateUserRequest request,
                                                    Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());

        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setPassword(request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : user.getPassword());
        user.setFirstName(request.getFirstName() != null ? request.getFirstName() : user.getFirstName());
        user.setLastName(request.getLastName() != null ? request.getLastName() : user.getLastName());
        if (request.getRole() != null) {
            try {
                user.setRole(Role.valueOf(request.getRole()).toString());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Invalid role type.", HttpStatus.BAD_REQUEST);
            }
        }
        user.setRole(request.getRole() != null ? request.getRole() : user.getRole());

        User updatedUser = userRepository.save(user);

        return new ResponseEntity<>(updatedUser.getId().toHexString(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete current user", notes = "${MyUserController.deleteCurrentUser.notes}")
    @DeleteMapping
    public ResponseEntity<String> deleteCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User deletedUser = userRepository.deleteByEmail(userDetails.getUsername());
        return new ResponseEntity<>(deletedUser.getId().toHexString(), HttpStatus.NO_CONTENT);
    }
}
