package com.jjstudio.dto.keyboard;

import java.util.Map;

public class CreateKeyboardRequest {

    private String name;

    private Map<String, String> mapping;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

}
