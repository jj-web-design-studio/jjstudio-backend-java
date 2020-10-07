package com.jjstudio.resource;

import com.jjstudio.model.Sound;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface SoundRepository extends CrudRepository<Sound, ObjectId> {

}
