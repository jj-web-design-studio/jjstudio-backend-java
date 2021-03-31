package com.jjstudio.controller;

import com.jjstudio.model.Keyboard;
import com.jjstudio.resource.KeyboardRepository;
import com.jjstudio.service.AuthUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/keyboards")
@CrossOrigin("http://localhost:3000")
public class KeyboardController {

    private final Logger logger = LoggerFactory.getLogger(KeyboardController.class);

    @Autowired
    private KeyboardRepository keyboardRepository;

    @Autowired
    private AuthUtil authUtil;

    @ApiOperation(value = "Get default keyboard", notes = "${KeyboardController.getDefaultKeyboard.notes}")
    @GetMapping("/default")
    public ResponseEntity<Keyboard> getDefaultKeyboard() {
        return new ResponseEntity<>(keyboardRepository.findByIsDefault(true), HttpStatus.OK);
    }

}
