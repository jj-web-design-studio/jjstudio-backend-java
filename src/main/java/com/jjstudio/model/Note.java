package com.jjstudio.model;

import org.bson.types.ObjectId;

public class Note {

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
