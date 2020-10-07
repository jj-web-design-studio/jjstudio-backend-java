package com.jjstudio.model;

import com.jjstudio.util.TimeSignature;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;

public class Track {

    @MongoId(value = FieldType.OBJECT_ID)
    private ObjectId id;

    private String name;

    private TimeSignature timeSignature;

    private ArrayList<ArrayList<Sound>> contents;

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

    public ArrayList<ArrayList<Sound>> getContents() {
        return contents;
    }

    public void setContents(ArrayList<ArrayList<Sound>> contents) {
        this.contents = contents;
    }

}
