package com.jjstudio.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jjstudio.dto.keyboard.Key;
import com.jjstudio.util.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "keyboards")
public class Keyboard {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    private String name;

    private String username;

    private List<Key> numRow;

    private List<Key> qweRow;

    private List<Key> asdRow;

    private List<Key> zxcRow;

    private boolean isDefault;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public List<Key> getNumRow() {
        return numRow;
    }

    public void setNumRow(List<Key> numRow) {
        this.numRow = numRow;
    }

    public List<Key> getQweRow() {
        return qweRow;
    }

    public void setQweRow(List<Key> qweRow) {
        this.qweRow = qweRow;
    }

    public List<Key> getAsdRow() {
        return asdRow;
    }

    public void setAsdRow(List<Key> asdRow) {
        this.asdRow = asdRow;
    }

    public List<Key> getZxcRow() {
        return zxcRow;
    }

    public void setZxcRow(List<Key> zxcRow) {
        this.zxcRow = zxcRow;
    }

}
