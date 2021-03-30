package com.jjstudio.dto.keyboard;

public class Key {

    private Integer keyCode;

    private Character keyLabel;

    private String soundId;

    private String soundLabel;

    public Key(Integer keyCode, Character keyLabel, String soundId, String soundLabel) {
        this.keyCode = keyCode;
        this.keyLabel = keyLabel;
        this.soundId = soundId;
        this.soundLabel = soundLabel;
    }

    public Integer getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(Integer keyCode) {
        this.keyCode = keyCode;
    }

    public Character getKeyLabel() {
        return keyLabel;
    }

    public void setKeyLabel(Character keyLabel) {
        this.keyLabel = keyLabel;
    }

    public String getSoundId() {
        return soundId;
    }

    public void setSoundId(String soundId) {
        this.soundId = soundId;
    }

    public String getSoundLabel() {
        return soundLabel;
    }

    public void setSoundLabel(String soundLabel) {
        this.soundLabel = soundLabel;
    }

}
