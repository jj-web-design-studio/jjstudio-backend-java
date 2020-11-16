package com.jjstudio.resource;

import com.jjstudio.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByEmail(String email);

    @Query(delete = true)
    User deleteByEmail(String email);

}
