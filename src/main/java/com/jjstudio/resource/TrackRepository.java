package com.jjstudio.resource;

import com.jjstudio.model.Track;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TrackRepository extends CrudRepository<Track, ObjectId> {

    Track findByIdAndUsername(ObjectId id, String username);

    Iterable<Track> findByUsername(String username);

    @Query(delete = true)
    Track deleteByIdAndUsername(ObjectId id, String username);

}
