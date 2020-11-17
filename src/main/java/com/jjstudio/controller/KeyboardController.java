package com.jjstudio.controller;

import com.jjstudio.dto.keyboard.CreateKeyboardRequest;
import com.jjstudio.model.Keyboard;
import com.jjstudio.resource.KeyboardRepository;
import com.jjstudio.util.AuthUtil;
import com.jjstudio.util.Keys;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Rest controller for Keyboard entity
 * @author justinchung
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/me/keyboards")
@CrossOrigin(origins = "*", maxAge = 3600)
public class KeyboardController {

    private final Logger logger = LoggerFactory.getLogger(KeyboardController.class);

    @Autowired
    private KeyboardRepository keyboardRepository;

    @ApiOperation(value = "Create a keyboard", notes = "${KeyboardController.createKeyboard.notes}")
    @PostMapping
    public ResponseEntity<String> createKeyboard(@RequestBody CreateKeyboardRequest request,
                                                 Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!AuthUtil.isPaidUser(userDetails.getAuthorities())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!isValidCreateKeyboardMapping(request.getMapping())) {
            return new ResponseEntity<>("Invalid keyboard mapping configuration", HttpStatus.BAD_REQUEST);
        }

        Keyboard keyboard = new Keyboard();
        keyboard.setName(request.getName());
        keyboard.setMapping(request.getMapping());
        keyboard.setUsername(userDetails.getUsername());

        Keyboard savedKeyboard = keyboardRepository.save(keyboard);

        return new ResponseEntity<>(savedKeyboard.getId().toHexString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a keyboard", notes = "${KeyboardController.getKeyboardById.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Keyboard> getKeyboardById(@PathVariable String id,
                                                    Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(keyboardRepository.findByIdAndUsername(new ObjectId(id), userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all keyboards", notes = "${KeyboardController.getAllKeyboards.notes}")
    @GetMapping
    public ResponseEntity<Iterable<Keyboard>> getAllKeyboards(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(keyboardRepository.findByUsername(userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a keyboard", notes = "${KeyboardController.deleteKeyboardById.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKeyboardById(@PathVariable String id,
                                                     Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Keyboard deletedKeyboard = keyboardRepository.deleteByIdAndUsername(new ObjectId(id), userDetails.getUsername());

        return new ResponseEntity<>(deletedKeyboard.getId().toHexString(), HttpStatus.OK);
    }

    private boolean isValidCreateKeyboardMapping(Map<String, String> mapping) {
        Map<String, Boolean> visited = new HashMap<>();
        for (String keyCode : mapping.keySet()) {
            if (visited.containsKey(keyCode)) {
                return false;
            }
            if (!Keys.exists(Integer.parseInt(keyCode))) {
                return false;
            }
            visited.put(keyCode, true);
        }
        return true;
    }
}
