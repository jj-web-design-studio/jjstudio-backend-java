package com.jjstudio.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection="users")
public class User {

    @MongoId(value = FieldType.OBJECT_ID)
    private ObjectId id;

    private String email;

    private String password;

    private Date dateJoined;

    private String firstName;

    private String lastName;

    private String userName;

    public User() {
    }

    public User(ObjectId id, String email, String password, Date dateJoined, String firstName, String lastName, String userName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.dateJoined = dateJoined;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
