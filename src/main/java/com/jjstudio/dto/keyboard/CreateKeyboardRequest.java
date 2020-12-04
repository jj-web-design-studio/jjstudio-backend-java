package com.jjstudio.dto.keyboard;

import java.util.Map;

public class CreateKeyboardRequest {

    private String name;

    private Map<String, String> numRow;

    private Map<String, String> qweRow;

    private Map<String, String> asdRow;

    private Map<String, String> zxcRow;

    private boolean isDefault;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
