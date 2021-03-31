package com.jjstudio.controller.me;

import com.jjstudio.dto.keyboard.Key;
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
import java.util.List;
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
public class MyKeyboardController {

    private final Logger logger = LoggerFactory.getLogger(MyKeyboardController.class);

    @Autowired
    private KeyboardRepository keyboardRepository;

    @Autowired
    private AuthUtil authUtil;

    @ApiOperation(value = "Create a keyboard for current user", notes = "${MyKeyboardController.createKeyboard.notes}")
    @PostMapping
    public ResponseEntity<String> createKeyboard(@RequestBody Keyboard request,
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

        Keyboard savedKeyboard = keyboardRepository.save(request);

        return new ResponseEntity<>(savedKeyboard.getId().toHexString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a keyboard for current user", notes = "${MyKeyboardController.getKeyboardById.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Keyboard> getKeyboardById(@PathVariable String id,
                                                    Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(keyboardRepository.findByIdAndUsername(new ObjectId(id), userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all keyboards for current user", notes = "${MyKeyboardController.getAllKeyboards.notes}")
    @GetMapping
    public ResponseEntity<Iterable<Keyboard>> getAllKeyboards(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(keyboardRepository.findByUsername(userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a keyboard for current user", notes = "${MyKeyboardController.deleteKeyboardById.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKeyboardById(@PathVariable String id,
                                                     Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Keyboard deletedKeyboard = keyboardRepository.deleteByIdAndUsername(new ObjectId(id), userDetails.getUsername());

        return new ResponseEntity<>(deletedKeyboard.getId().toHexString(), HttpStatus.OK);
    }

    private boolean isValidCreateKeyboardMapping(List<Key> numRow,
                                                 List<Key> qweRow,
                                                 List<Key> asdRow,
                                                 List<Key> zxcRow) {
        return isValidKeyRowMapping(numRow, Keys.Rows.NUM)
                && isValidKeyRowMapping(qweRow, Keys.Rows.QWE)
                && isValidKeyRowMapping(asdRow, Keys.Rows.ASD)
                && isValidKeyRowMapping(zxcRow, Keys.Rows.ZXC);
    }

    private boolean isValidKeyRowMapping(List<Key> row, int expectedRow) {
        if (row != null) {
            Map<Integer, Boolean> visited = new HashMap<>();
            for (Key key : row) {
                if (visited.containsKey(key.getKeyCode())) {
                    return false;
                }
                if (!Keys.keyCodeExists(key.getKeyCode())) {
                    return false;
                }
                if (!Keys.keyCodeIsOfRow(key.getKeyCode(), expectedRow)) {
                    return false;
                }
                visited.put(key.getKeyCode(), true);
            }
        }
        return true;
    }
}
