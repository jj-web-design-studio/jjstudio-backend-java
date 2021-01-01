package com.jjstudio.dto.track;

import com.jjstudio.model.Note;
import com.jjstudio.util.TimeSignature;

import java.util.List;

public class SaveTrackRequest {

    private String name;

    private TimeSignature timeSignature;

    private Integer bpm;

    private Integer rowCount;

    private List<List<Note>> contents;

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

    public Integer getBpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public List<List<Note>> getContents() {
        return contents;
    }

    public void setContents(List<List<Note>> contents) {
        this.contents = contents;
    }

}
