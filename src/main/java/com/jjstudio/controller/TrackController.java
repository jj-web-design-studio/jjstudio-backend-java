package com.jjstudio.controller;

import com.jjstudio.dto.track.SaveTrackRequest;
import com.jjstudio.model.Track;
import com.jjstudio.resource.TrackRepository;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Save a track", notes = "${TrackController.saveTrack.notes}")
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

    @ApiOperation(value = "Get a track", notes = "${TrackController.getTrackByUserAndId.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackByUserAndId(@PathVariable String id, @RequestParam String username) {
        return new ResponseEntity<>(trackRepository.findByIdAndUsername(new ObjectId(id), username), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all tracks for user", notes = "${TrackController.getAllTracksForUser.notes}")
    @GetMapping
    public ResponseEntity<Iterable<Track>> getAllTracksForUser(@RequestParam String username) {
        return new ResponseEntity<>(trackRepository.findByUsername(username), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a track", notes = "${TrackController.deleteTrackByUserAndId.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTrackByUserAndId(@PathVariable String id, @RequestParam String username) {
        Track deletedTrack = trackRepository.deleteByIdAndUsername(new ObjectId(id), username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
