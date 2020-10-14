package com.jjstudio.dto.track;

import com.jjstudio.util.TimeSignature;

public class SaveTrackRequest {

    private String name;

    private TimeSignature timeSignature;

    private Integer[][] contents;

    private String username;

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
