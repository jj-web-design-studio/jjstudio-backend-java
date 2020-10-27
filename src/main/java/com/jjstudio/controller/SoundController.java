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
@CrossOrigin(origins = "*", maxAge = 3600)
public class SoundController {

    private final Logger logger = LoggerFactory.getLogger(SoundController.class);

    @Autowired
    private SoundRepository soundRepository;

    @ApiOperation(value = "Upload and save a sound for current user", notes = "${SoundController.saveSound.notes}")
    @PostMapping
    public ResponseEntity saveSound(@RequestParam("file") MultipartFile multipartFile,
                                    @RequestParam("name") String name,
                                    @RequestParam("username") String username) {
        if (multipartFile.getSize() > 15000000) {   // 15MB
            return new ResponseEntity<>("The file is too large. The maximum file size allowed is 15MB.", HttpStatus.BAD_REQUEST);
        }

        Sound savedSound;

        try {
            Sound sound = new Sound();
            sound.setDefault(false);
            sound.setName(name);
            sound.setFile(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            sound.setUsername(username);
            savedSound = soundRepository.save(sound);
        } catch (IOException e) {
            logger.error("An error occurred while reading MultipartFile object", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedSound.getId().toHexString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a sound for current user", notes = "${SoundController.getSoundByIdAndUser.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Sound> getSoundByIdAndUser(@PathVariable String id,
                                                     @RequestParam String username) {
        return new ResponseEntity<>(soundRepository.findByIdAndUsername(new ObjectId(id), username), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all sounds for current user", notes = "${SoundController.getAllSoundsForUser.notes}")
    @GetMapping
    public ResponseEntity<Iterable<Sound>> getAllSoundsForUser(@RequestParam String username,
                                                               @RequestParam(value = "ids", required = false) List<String> ids) {
        if (ids != null && ids.size() > 0) {
            logger.info("id list is not null");
            List<ObjectId> objectIds = new ArrayList<>();
            for (String id : ids) {
                ObjectId objectId = new ObjectId(id);
                objectIds.add(objectId);
            }
            return new ResponseEntity<>(soundRepository.findAllById(objectIds), HttpStatus.OK);
        }
        return new ResponseEntity<>(soundRepository.findByUsername(username), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a sound for current user", notes = "${SoundController.deleteSoundByIdAndUser.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteSoundByIdAndUser(@PathVariable String id,
                                                 @RequestParam String username) {
        Sound sound = soundRepository.deleteByIdAndUsername(new ObjectId(id), username);
        if (sound.getId() != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("ObjectId " + sound.getId() + " does not match any user with username " + username, HttpStatus.BAD_REQUEST);
        }
    }

}
