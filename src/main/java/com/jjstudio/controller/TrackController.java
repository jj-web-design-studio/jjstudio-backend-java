package com.jjstudio.controller;

import com.jjstudio.model.Track;
import com.jjstudio.resource.TrackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final Logger logger = LoggerFactory.getLogger(SoundController.class);

    public static final String TRACKS_SWAGGER_GROUP_NAME = "Tracks";

    @Autowired
    private TrackRepository trackRepository;

    @PostMapping
    public ResponseEntity saveTrack() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Integer id) {
        return new ResponseEntity<>(trackRepository.findById(id).orElse(null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Track>> getAllTracks() {
        return new ResponseEntity<>(trackRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTrack(@PathVariable String id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
