package com.jjstudio.controller;

import com.jjstudio.model.Sound;
import com.jjstudio.resource.SoundRepository;
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

/**
 * @author justinchung
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/sounds")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SoundController {

    private final Logger logger = LoggerFactory.getLogger(SoundController.class);

    @Autowired
    private SoundRepository soundRepository;

    /**
     * Save a sound file that cannot be larger than 15MB. This is a strict limit
     * from MongoDB asa single document can only be 16MB large. 1MB is left for
     * other potential fields.
     * @param multipartFile file to be converted to base64 for storage
     * @param name          name of sound file determined by the user
     * @param username      username
     * @return              HTTP response code representing success/fail
     */
    @PostMapping
    public ResponseEntity saveSound(@RequestParam("file") MultipartFile multipartFile,
                                    @RequestParam("name") String name,
                                    @RequestParam("username") String username) {
        if (multipartFile.getSize() > 15000000) {   // 15MB
            return new ResponseEntity<>("The file is too large. The maximum file size allowed is 15MB.", HttpStatus.BAD_REQUEST);
        }

        try {
            Sound sound = new Sound();
            sound.setDefault(false);
            sound.setName(name);
            sound.setFile(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
            sound.setUsername(username);
            soundRepository.save(sound);
        } catch (IOException e) {
            logger.error("An error occurred while reading MultipartFile object", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Fetch a sound based on ObjectId and username. A sound can only be read by
     * the user that uploaded it.
     * @param id        ObjectId, as a string
     * @param username  username
     * @return          single sound
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sound> getSoundByIdAndUser(@PathVariable String id,
                                                     @RequestParam String username) {
        return new ResponseEntity<>(soundRepository.findByIdAndUsername(new ObjectId(id), username), HttpStatus.OK);
    }

    /**
     * Get all sounds associated with a given username. A sound can only be read
     * by the user that uploaded it.
     * @param username  username
     * @return          list of sounds
     */
    @GetMapping
    public ResponseEntity<Iterable<Sound>> getAllSoundsForUser(@RequestParam String username) {
        return new ResponseEntity<>(soundRepository.findByUsername(username), HttpStatus.OK);
    }

    /**
     * Delete a single sound associated with a given ObjectId and username. A
     * sound can only be deleted by the user that uploaded it.
     * @param id        ObjectId, as a string
     * @param username  username
     * @return          ObjectId of deleted sound
     */
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
