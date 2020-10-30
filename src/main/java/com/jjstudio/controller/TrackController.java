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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for Track entity
 * @author justinchung
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/me/tracks")
public class TrackController {

    private final Logger logger = LoggerFactory.getLogger(TrackController.class);

    @Autowired
    private TrackRepository trackRepository;

    @ApiOperation(value = "Save a track for current user", notes = "${TrackController.saveTrack.notes}")
    @PostMapping
    public ResponseEntity createTrack(@RequestBody SaveTrackRequest request,
                                    Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Track track = new Track();
        track.setName(request.getName());
        track.setTimeSignature(request.getTimeSignature());
        track.setContents(request.getContents());
        track.setUsername(userDetails.getUsername());

        Track savedTracked = trackRepository.save(track);

        return new ResponseEntity<>(savedTracked.getId().toHexString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a track for current user", notes = "${TrackController.getTrackByUserAndId.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable String id,
                                              Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(trackRepository.findByIdAndUsername(new ObjectId(id), userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all tracks for current user", notes = "${TrackController.getAllTracksForUser.notes}")
    @GetMapping
    public ResponseEntity<Iterable<Track>> getAllTracks(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(trackRepository.findByUsername(userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a track for current user", notes = "${TrackController.deleteTrackByUserAndId.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTrackById(@PathVariable String id,
                                          Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Track deletedTrack = trackRepository.deleteByIdAndUsername(new ObjectId(id), userDetails.getUsername());
        return new ResponseEntity<>(deletedTrack.getId().toHexString(), HttpStatus.NO_CONTENT);
    }

}
