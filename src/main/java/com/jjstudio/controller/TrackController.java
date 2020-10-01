package com.jjstudio.controller;

import com.jjstudio.model.Track;
import com.jjstudio.resource.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @PostMapping
    public ResponseEntity saveTrack() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> fetchTrackById(@PathVariable Integer id) {
        return new ResponseEntity<>(trackRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Track>> fetchAllTracks() {
        return new ResponseEntity<>(trackRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTrack(@PathVariable String id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
