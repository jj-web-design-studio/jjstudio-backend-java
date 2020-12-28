package com.jjstudio.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jjstudio.util.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Map;

@Document(collection = "keyboards")
public class Keyboard {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    private String name;

    private String username;

    private Map<String, String> numRow;

    private Map<String, String> qweRow;

    private Map<String, String> asdRow;

    private Map<String, String> zxcRow;

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

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Map<String, String> getNumRow() {
        return numRow;
    }

    public void setNumRow(Map<String, String> numRow) {
        this.numRow = numRow;
    }

    public Map<String, String> getQweRow() {
        return qweRow;
    }

    public void setQweRow(Map<String, String> qweRow) {
        this.qweRow = qweRow;
    }

    public Map<String, String> getAsdRow() {
        return asdRow;
    }

    public void setAsdRow(Map<String, String> asdRow) {
        this.asdRow = asdRow;
    }

    public Map<String, String> getZxcRow() {
        return zxcRow;
    }

    public void setZxcRow(Map<String, String> zxcRow) {
        this.zxcRow = zxcRow;
    }

}
