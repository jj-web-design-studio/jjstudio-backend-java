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

@RestController
@RequestMapping("/sounds")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SoundController {

    private final Logger logger = LoggerFactory.getLogger(SoundController.class);

    public static final String SOUNDS_SWAGGER_GROUP_NAME = "Sounds";

    @Autowired
    private SoundRepository soundRepository;

    @PostMapping
    public ResponseEntity saveSound(@RequestParam("file") MultipartFile multipartFile, @RequestParam("name") String name, @RequestParam("username") String username) {
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

    @GetMapping("/{id}")
    public ResponseEntity<Sound> getSoundById(@PathVariable String id) {
        return new ResponseEntity<>(soundRepository.findById(new ObjectId(id)).orElse(null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Sound>> getAllSounds() {
        return new ResponseEntity<>(soundRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSound(@PathVariable String id) {
        soundRepository.deleteById(new ObjectId(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
