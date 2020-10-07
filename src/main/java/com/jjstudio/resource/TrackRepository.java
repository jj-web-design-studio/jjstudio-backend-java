package com.jjstudio.resource;

import com.jjstudio.model.Track;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface TrackRepository extends CrudRepository<Track, ObjectId> {

}
