package com.jjstudio.util;

import java.util.HashSet;
import java.util.Set;

public enum Keys {

    // 123 Row
    ONE("1", 49),
    TWO("2", 50),
    THREE("3", 51),
    FOUR("4", 52),
    FIVE("5", 53),
    SIX("6", 54),
    SEVEN("7", 55),
    EIGHT("8", 56),
    NINE("9", 57),
    ZERO("0", 48),
    DASH("-", 189),
    EQUAL_SIGN("=", 187),

    // QWE Row
    Q("Q", 81),
    W("W", 87),
    E("E", 69),
    R("R", 82),
    T("T", 84),
    Y("Y", 89),
    U("U", 85),
    I("I", 73),
    O("O", 79),
    P("P", 80),
    OPEN_BRACKET("[", 219),
    CLOSE_BRACKET("]", 221),

    // ASD Row
    A("A", 65),
    S("S", 83),
    D("D", 68),
    F("F", 70),
    G("G", 71),
    H("H", 72),
    J("J", 74),
    K("K", 75),
    L("L", 76),
    SEMI_COLON(";", 186),
    SINGLE_QUOTE("'", 222),

    // ZXC Row
    Z("Z", 90),
    X("X", 88),
    C("C", 67),
    V("V", 86),
    B("B",66),
    N("N", 78),
    M("M", 77),
    COMMA(",", 188),
    PERIOD(".", 190),
    SLASH("/", 191);

    private final String name;

    private final int code;

    Keys(String name, int code) {
        this.name = name;
        this.code = code;
    }

    private final static Set<Integer> keyCodes;

    static {
        keyCodes = new HashSet<>();
        for(Keys key : Keys.values()) {
            keyCodes.add(key.code);
        }
    }

    public static boolean exists(Integer keyCode) {
        return keyCodes.contains(keyCode);
    }

}
