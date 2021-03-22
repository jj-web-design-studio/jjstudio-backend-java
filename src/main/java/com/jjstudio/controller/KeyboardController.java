package com.jjstudio.controller;

import com.jjstudio.dto.keyboard.CreateKeyboardRequest;
import com.jjstudio.model.Keyboard;
import com.jjstudio.resource.KeyboardRepository;
import com.jjstudio.service.AuthUtil;
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
 *
 * @author justinchung
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/me/keyboards")
@CrossOrigin("http://localhost:3000")
public class KeyboardController {

    private final Logger logger = LoggerFactory.getLogger(KeyboardController.class);

    @Autowired
    private KeyboardRepository keyboardRepository;

    @Autowired
    private AuthUtil authUtil;

    @ApiOperation(value = "Create a keyboard", notes = "${KeyboardController.createKeyboard.notes}")
    @PostMapping
    public ResponseEntity<String> createKeyboard(@RequestBody CreateKeyboardRequest request,
                                                 Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!isValidCreateKeyboardMapping(request.getNumRow(), request.getQweRow(), request.getAsdRow(), request.getZxcRow())) {
            return new ResponseEntity<>("Invalid keyboard mapping configuration", HttpStatus.BAD_REQUEST);
        }

        if (authUtil.isPaidUser(userDetails.getAuthorities())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (request.getName().toUpperCase().equals("DEFAULT")) {
            return new ResponseEntity<>("This keyboard name already exists!", HttpStatus.BAD_REQUEST);
        }

        Keyboard keyboard = new Keyboard();
        keyboard.setName(request.getName());
        keyboard.setNumRow(request.getNumRow());
        keyboard.setQweRow(request.getQweRow());
        keyboard.setAsdRow(request.getAsdRow());
        keyboard.setZxcRow(request.getZxcRow());
        keyboard.setUsername(userDetails.getUsername());
        keyboard.setDefault(request.isDefault());

        Keyboard savedKeyboard = keyboardRepository.save(keyboard);

        return new ResponseEntity<>(savedKeyboard.getId().toHexString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a keyboard", notes = "${KeyboardController.getKeyboardById.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Keyboard> getKeyboardById(@PathVariable String id,
                                                    Authentication authentication) {
        if ("default".equals(id)) {
            Keyboard keyboard = keyboardRepository.findByIsDefault(true);
            if (keyboard == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(keyboard, HttpStatus.OK);
        }

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

    private boolean isValidCreateKeyboardMapping(Map<String, String> numRow,
                                                 Map<String, String> qweRow,
                                                 Map<String, String> asdRow,
                                                 Map<String, String> zxcRow) {
        return isValidKeyRowMapping(numRow, Keys.Rows.NUM)
                && isValidKeyRowMapping(qweRow, Keys.Rows.QWE)
                && isValidKeyRowMapping(asdRow, Keys.Rows.ASD)
                && isValidKeyRowMapping(zxcRow, Keys.Rows.ZXC);
    }

    private boolean isValidKeyRowMapping(Map<String, String> row, int expectedRow) {
        if (row != null) {
            Map<String, Boolean> visited = new HashMap<>();
            for (String keyCode : row.keySet()) {
                if (visited.containsKey(keyCode)) {
                    return false;
                }
                if (!Keys.keyCodeExists(Integer.parseInt(keyCode))) {
                    return false;
                }
                if (!Keys.keyCodeIsOfRow(Integer.parseInt(keyCode), expectedRow)) {
                    return false;
                }
                visited.put(keyCode, true);
            }
        }
        return true;
    }
}
