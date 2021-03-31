package com.jjstudio.controller.me;

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
 *
 * @author justinchung
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/v1/me/tracks")
@CrossOrigin("http://localhost:3000")
public class MyTrackController {

    private final Logger logger = LoggerFactory.getLogger(MyTrackController.class);

    @Autowired
    private TrackRepository trackRepository;

    @ApiOperation(value = "Save a track for current user", notes = "${MyTrackController.createTrack.notes}")
    @PostMapping
    public ResponseEntity<String> createTrack(@RequestBody Track request,
                                              Authentication authentication) {
        if (!isValidSaveRequest(request)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        request.setUsername(userDetails.getUsername());
        Track savedTracked = trackRepository.save(request);

        return new ResponseEntity<>(savedTracked.getId().toHexString(), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a track for current user", notes = "${MyTrackController.getTrackById.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable String id,
                                              Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(trackRepository.findByIdAndUsername(new ObjectId(id), userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all tracks for current user", notes = "${MyTrackController.getAllTracks.notes}")
    @GetMapping
    public ResponseEntity<Iterable<Track>> getAllTracks(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(trackRepository.findByUsername(userDetails.getUsername()), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a track for current user", notes = "${MyTrackController.deleteTrackById.notes}")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrackById(@PathVariable String id,
                                                  Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Track deletedTrack = trackRepository.deleteByIdAndUsername(new ObjectId(id), userDetails.getUsername());
        return new ResponseEntity<>(deletedTrack.getId().toHexString(), HttpStatus.OK);
    }

    private boolean isValidSaveRequest(Track request) {
        return request.getName() != null &&
                request.getContents() != null &&
                request.getContents().size() > 0 &&
                request.getContents().get(0).size() > 0 &&
                request.getTimeSignature() != null;
    }
}
