package com.jjstudio.controller;

import com.jjstudio.model.Sound;
import com.jjstudio.resource.SoundRepository;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/sounds")
@CrossOrigin("http://localhost:3000")
public class SoundController {

    private final Logger logger = LoggerFactory.getLogger(SoundController.class);

    @Autowired
    private SoundRepository soundRepository;

    @ApiOperation(value = "Get a sound", notes = "${SoundController.getSoundById.notes}")
    @GetMapping("/{id}")
    public ResponseEntity<Sound> getSoundById(@PathVariable String id) {
        return new ResponseEntity(soundRepository.findById(new ObjectId(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all sounds", notes = "${SoundController.getAllSounds.notes}")
    @GetMapping
    public ResponseEntity<Iterable<Sound>> getAllSounds(@RequestParam(value = "ids", required = false) List<String> ids) {
        if (ids != null && ids.size() > 0) {
            List<ObjectId> objectIds = new ArrayList<>();
            for (String id : ids) {
                ObjectId objectId = new ObjectId(id);
                objectIds.add(objectId);
            }
            return new ResponseEntity<>(soundRepository.findAllById(objectIds), HttpStatus.OK);
        }
        return new ResponseEntity<>(soundRepository.findAll(), HttpStatus.OK);
    }
}
