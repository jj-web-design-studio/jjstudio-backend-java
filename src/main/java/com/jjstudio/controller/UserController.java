package com.jjstudio.controller;

import com.jjstudio.dto.user.CreateUserRequest;
import com.jjstudio.dto.user.UpdateUserRequest;
import com.jjstudio.exception.UserNotFoundException;
import com.jjstudio.resource.UserRepository;
import com.jjstudio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setDateJoined(new Timestamp(System.currentTimeMillis()));

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> readUser(@PathVariable Integer id) throws UserNotFoundException {
        return new ResponseEntity<>(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        User user = userRepository.findById(id).get();
        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setPassword(request.getPassword() != null ? request.getPassword() : user.getPassword());
        user.setFirstName(request.getFirstName() != null ? request.getFirstName() : user.getFirstName());
        user.setLastName(request.getLastName() != null ? request.getLastName() : user.getLastName());
        user.setUserName(request.getUserName() != null ? request.getUserName() : user.getUserName());

        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
