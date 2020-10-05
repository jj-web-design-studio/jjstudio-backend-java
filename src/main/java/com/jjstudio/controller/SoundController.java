package com.jjstudio.controller;

import com.jjstudio.dto.sound.SaveSoundRequest;
import com.jjstudio.model.Sound;
import com.jjstudio.resource.SoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sounds")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SoundController {

    public static final String SOUNDS_SWAGGER_GROUP_NAME = "Sounds";

    @Autowired
    private SoundRepository soundRepository;

    @PostMapping
    public ResponseEntity saveSound(@RequestBody SaveSoundRequest request) {
        Sound sound = new Sound();
        sound.setDefault(false);
        sound.setName(request.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sound> getSoundById(@PathVariable Integer id) {
        return new ResponseEntity<>(soundRepository.findById(id).orElse(null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Sound>> getAllSounds() {
        return new ResponseEntity<>(soundRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSound(@PathVariable Integer id) {
        soundRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
