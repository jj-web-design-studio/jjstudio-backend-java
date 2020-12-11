package com.jjstudio.model;

import com.jjstudio.util.TimeSignature;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection="tracks")
public class Track {

    @MongoId(value = FieldType.OBJECT_ID)
    private ObjectId id;

    private String name;

    private TimeSignature timeSignature;

    private Integer[][] contents;

    private String username;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        this.timeSignature = timeSignature;
    }

    public Integer[][] getContents() {
        return contents;
    }

    public void setContents(Integer[][] contents) {
        this.contents = contents;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
