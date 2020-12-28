package com.jjstudio.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jjstudio.util.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "notes")
public class Note {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId soundId;

    private Float left;

    private Integer row;

    public ObjectId getSoundId() {
        return soundId;
    }

    public void setSoundId(ObjectId soundId) {
        this.soundId = soundId;
    }

    public Float getLeft() {
        return left;
    }

    public void setLeft(Float left) {
        this.left = left;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }
}
