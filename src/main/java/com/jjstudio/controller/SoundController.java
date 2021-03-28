package com.jjstudio.controller;

import com.jjstudio.model.Sound;
import com.jjstudio.resource.SoundRepository;
import io.swagger.annotations.ApiOperation;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest controller for Sound entity
 * @author justinchung
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/me/sounds")
@CrossOrigin("http://localhost:3000")
public class SoundController {

    private final Logger logger = LoggerFactory.getLogger(SoundController.class);

    @Autowired
    private SoundRepository soundRepository;

    @ApiOperation(value = "Create a sound", notes = "${SoundController.createSound.notes}")
    @PostMapping
    public ResponseEntity<String> createSound(@RequestParam("file") MultipartFile multipartFile,
                                              @RequestParam("name") String name,
                                              Authentication authentication) {
        if (multipartFile.getSize() > 15000000) {   // 15MB
            return new ResponseEntity<>("The file is too large. The maximum file size allowed is 15MB.", HttpStatus.BAD_REQUEST);
        }

        Sound savedSound;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        try {
            Sound sound = new Sound();
            sound.setDefault(false);
            sound.setName(name);
            sound.setFile(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            sound.setUsername(userDetails.getUsername());
            savedSound = soundRepository.save(sound);
        } catch (IOException e) {
            logger.error("An error occurred while reading MultipartFile object", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedSound.getId().toHexString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a sound", notes = "${SoundController.getSoundById.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Sound> getSoundById(@PathVariable String id,
                                              Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(soundRepository.findByIdAndUsername(new ObjectId(id), userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all sounds", notes = "${SoundController.getAllSounds.notes}")
    @GetMapping
    public ResponseEntity<Iterable<Sound>> getAllSounds(@RequestParam(value = "ids", required = false) List<String> ids,
                                                        Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (ids != null && ids.size() > 0) {
            logger.debug("id list is not null");
            List<ObjectId> objectIds = new ArrayList<>();
            for (String id : ids) {
                ObjectId objectId = new ObjectId(id);
                objectIds.add(objectId);
            }
            return new ResponseEntity<>(soundRepository.findAllById(objectIds), HttpStatus.OK);
        }
        return new ResponseEntity<>(soundRepository.findByUsername(userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a sound", notes = "${SoundController.deleteSoundById.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSoundById(@PathVariable String id,
                                                  Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Sound sound = soundRepository.deleteByIdAndUsername(new ObjectId(id), userDetails.getUsername());
        if (sound.getId() != null) {
            return new ResponseEntity<>(sound.getId().toHexString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ObjectId " + id + " does not match any user with username " + userDetails.getUsername(), HttpStatus.BAD_REQUEST);
        }
    }

}
