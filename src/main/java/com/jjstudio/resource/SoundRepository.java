package com.jjstudio.resource;

import com.jjstudio.model.Sound;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SoundRepository extends CrudRepository<Sound, ObjectId> {

    Sound findByIdAndUsername(ObjectId id, String username);

    Iterable<Sound> findByUsername(String username);

    Iterable<Sound> findAllById(Iterable<ObjectId> id);

    @Query(delete = true)
    Sound deleteByIdAndUsername(ObjectId id, String username);

}
