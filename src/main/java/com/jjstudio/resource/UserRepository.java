package com.jjstudio.resource;

import com.jjstudio.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByEmail(String email);

    User findByUserName(String userName);
}
