package com.jjstudio.controller;

import com.jjstudio.dto.user.CreateUserRequest;
import com.jjstudio.dto.user.UpdateUserRequest;
import com.jjstudio.exception.UserNotFoundException;
import com.jjstudio.resource.UserRepository;
import com.jjstudio.model.User;
import com.jjstudio.util.AuthUtil;
import com.jjstudio.util.Role;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * Rest controller for User entity
 * @author justinchung
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Create a new user", notes = "${UserController.createUser.notes}")
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        if (emailAlreadyExists(request.getEmail())) {
            return new ResponseEntity<>("A user with email " + request.getEmail() + " already exists.", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPassword(request.getPassword())) {
            return new ResponseEntity<>("Password is unsatisfactory. ", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDateJoined(new Timestamp(System.currentTimeMillis()));
        user.setRole(Role.FREE_USER.toString());

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser.getId().toHexString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a user", notes = "${UserController.getUser.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id,
                                        Authentication authentication) throws UserNotFoundException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!AuthUtil.isAdminUser(userDetails.getAuthorities())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userRepository.findById(new ObjectId(id)).orElseThrow(() -> new UserNotFoundException(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "Update a user", notes = "${UserController.updateUser.notes}")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id,
                                             @RequestBody UpdateUserRequest request) throws UserNotFoundException {
        User user = userRepository.findById(new ObjectId(id)).orElseThrow(() -> new UserNotFoundException(id));

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

    @ApiOperation(value = "Delete a user", notes = "${UserController.deleteUser.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userRepository.deleteById(new ObjectId(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get current user", notes = "${UserController.getCurrentUser.notes}")
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Update current user", notes = "${UserController.updateCurrentUser.notes}")
    @PostMapping("/me")
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

    @ApiOperation(value = "Delete current user", notes = "${UserController.deleteCurrentUser.notes}")
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User deletedUser = userRepository.deleteByEmail(userDetails.getUsername());
        return new ResponseEntity<>(deletedUser.getId().toHexString(), HttpStatus.NO_CONTENT);
    }

    private boolean emailAlreadyExists(String email) {
        User existingUser = userRepository.findByEmail(email);
        return existingUser != null;
    }

    private boolean isValidPassword(String password) {
        return password.length() > 5;
    }

}
