package com.jjstudio.model;

import com.jjstudio.util.TimeSignature;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document(collection="tracks")
public class Track {

    @MongoId(value = FieldType.OBJECT_ID)
    private ObjectId id;

    private String name;

    private TimeSignature timeSignature;

    private List<List<Note>> contents;

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

    public List<List<Note>> getContents() {
        return contents;
    }

    public void setContents(List<List<Note>> contents) {
        this.contents = contents;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
