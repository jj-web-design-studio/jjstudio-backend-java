package com.jjstudio.controller;

import com.jjstudio.dto.user.CreateUserRequest;
import com.jjstudio.dto.user.UpdateUserRequest;
import com.jjstudio.dto.user.CreateUserErrorResponse;
import com.jjstudio.exception.UserNotFoundException;
import com.jjstudio.resource.UserRepository;
import com.jjstudio.model.User;
import com.jjstudio.util.RoleEnum;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Create a new user", notes = "${UserController.createUser.notes}")
    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        if (emailAlreadyExists(request.getEmail())) {
            return new ResponseEntity<>(new CreateUserErrorResponse("A user with email " + request.getEmail() + " already exists."), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDateJoined(new Timestamp(System.currentTimeMillis()));
        user.setRole(RoleEnum.FREE_USER.toString());

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser.getId().toHexString(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers(@RequestParam(required = false) String email) {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        // Not sure if this endpoint is needed
    }

    @ApiOperation(value = "Get user information", notes = "${UserController.getUser.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) throws UserNotFoundException {
        return new ResponseEntity<>(userRepository.findById(new ObjectId(id)).orElseThrow(() -> new UserNotFoundException(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "Update user information", notes = "${UserController.updateUser.notes}")
    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request) throws UserNotFoundException {
        User user = userRepository.findById(new ObjectId(id)).orElseThrow(() -> new UserNotFoundException(id));

        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setPassword(request.getPassword() != null ? request.getPassword() : user.getPassword());
        user.setFirstName(request.getFirstName() != null ? request.getFirstName() : user.getFirstName());
        user.setLastName(request.getLastName() != null ? request.getLastName() : user.getLastName());

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a user", notes = "${UserController.deleteUser.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        userRepository.deleteById(new ObjectId(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean emailAlreadyExists(String email) {
        User existingUser = userRepository.findByEmail(email);
        return existingUser != null;
    }

}
