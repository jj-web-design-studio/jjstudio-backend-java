package com.jjstudio.resource;

import com.jjstudio.model.Track;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TrackRepository extends MongoRepository<Track, ObjectId> {

    Track findByIdAndUsername(ObjectId id, String username);

    Iterable<Track> findByUsername(String username);

    @Query(delete = true)
    Track deleteByIdAndUsername(ObjectId id, String username);

}
