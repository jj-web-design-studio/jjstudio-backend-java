package com.jjstudio.model;

import com.jjstudio.util.TimeSignature;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table
public class Track {
    private Integer id;

    private String name;

    private TimeSignature timeSignature;

    private ArrayList<ArrayList<Sound>> contents;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
