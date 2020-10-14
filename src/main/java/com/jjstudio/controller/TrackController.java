package com.jjstudio.controller;

import com.jjstudio.dto.track.SaveTrackRequest;
import com.jjstudio.model.Track;
import com.jjstudio.resource.TrackRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final Logger logger = LoggerFactory.getLogger(TrackController.class);

    @Autowired
    private TrackRepository trackRepository;

    @PostMapping
    public ResponseEntity saveTrack(@RequestBody SaveTrackRequest request) {
        Track track = new Track();
        track.setName(request.getName());
        track.setTimeSignature(request.getTimeSignature());
        track.setContents(request.getContents());
        track.setUsername(request.getUsername());

        Track savedTracked = trackRepository.save(track);

        return new ResponseEntity<>(savedTracked.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable String id, @RequestParam String username) {
        return new ResponseEntity<>(trackRepository.findByIdAndUsername(new ObjectId(id), username), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Track>> getAllTracks(@RequestParam String username) {
        return new ResponseEntity<>(trackRepository.findByUsername(username), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTrack(@PathVariable String id, @RequestParam String username) {
        Track deletedTrack = trackRepository.deleteByIdAndUsername(new ObjectId(id), username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
