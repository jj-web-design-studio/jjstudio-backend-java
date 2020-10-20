package com.jjstudio.controller;

import com.jjstudio.dto.user.CreateUserRequest;
import com.jjstudio.dto.user.UpdateUserRequest;
import com.jjstudio.dto.user.CreateUserErrorResponse;
import com.jjstudio.exception.UserNotFoundException;
import com.jjstudio.resource.UserRepository;
import com.jjstudio.model.User;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "Create a new user", notes = "${UserController.createUser.notes}")
    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        if (emailAlreadyExists(request.getEmail())) {
            return new ResponseEntity<>(new CreateUserErrorResponse("A user with email " + request.getEmail() + " already exists."), HttpStatus.BAD_REQUEST);
        } else if (userNameAlreadyExists(request.getUserName())) {
            return new ResponseEntity<>(new CreateUserErrorResponse("A user with username " + request.getUserName() + " already exists."), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setDateJoined(new Timestamp(System.currentTimeMillis()));

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(savedUser.getId().toHexString(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
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
        user.setUserName(request.getUserName() != null ? request.getUserName() : user.getUserName());

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a user", notes = "${UserController.deleteUser.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        userRepository.deleteById(new ObjectId(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean userNameAlreadyExists(String userName) {
        User existingUser = userRepository.findByUserName(userName);
        return existingUser != null;
    }

    private boolean emailAlreadyExists(String email) {
        User existingUser = userRepository.findByEmail(email);
        return existingUser != null;
    }

}
