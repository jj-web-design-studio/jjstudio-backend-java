package com.jjstudio.resource;

import com.jjstudio.model.Keyboard;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface KeyboardRepository extends MongoRepository<Keyboard, ObjectId> {

    Keyboard findByIdAndUsername(ObjectId id, String username);

    Iterable<Keyboard> findByUsername(String username);

    Iterable<Keyboard> findAllByIdAndUsername(ObjectId id, String username);

    Keyboard findByIsDefault(boolean isDefault);

    @Query(delete = true)
    Keyboard deleteByIdAndUsername(ObjectId id, String username);

}
