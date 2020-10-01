package com.jjstudio.util;

public enum TimeSignatureEnum {
    BEAT_2("beat", 2),
    BEAT_3("beat", 3),
    BEAT_4("beat", 4),
    BEAT_5("beat", 5),
    BEAT_6("beat", 6),
    BEAT_7("beat", 7),
    BEAT_8("beat", 8),

    MEASURE_4("measure", 4),
    MEASURE_6("measure", 6),
    MEASURE_8("measure", 8);

    private String type;
    private int value;
    TimeSignatureEnum(String type, int value) {
        this.type = type;
        this.value = value;
    }
}
