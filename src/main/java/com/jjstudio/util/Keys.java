package com.jjstudio.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum Keys {

    // 123 Row
    ONE("1", 49, Rows.NUM),
    TWO("2", 50, Rows.NUM),
    THREE("3", 51, Rows.NUM),
    FOUR("4", 52, Rows.NUM),
    FIVE("5", 53, Rows.NUM),
    SIX("6", 54, Rows.NUM),
    SEVEN("7", 55, Rows.NUM),
    EIGHT("8", 56, Rows.NUM),
    NINE("9", 57, Rows.NUM),
    ZERO("0", 48, Rows.NUM),
    DASH("-", 189, Rows.NUM),
    EQUAL_SIGN("=", 187, Rows.NUM),

    // QWE Row
    Q("Q", 81, Rows.QWE),
    W("W", 87, Rows.QWE),
    E("E", 69, Rows.QWE),
    R("R", 82, Rows.QWE),
    T("T", 84, Rows.QWE),
    Y("Y", 89, Rows.QWE),
    U("U", 85, Rows.QWE),
    I("I", 73, Rows.QWE),
    O("O", 79, Rows.QWE),
    P("P", 80, Rows.QWE),
    OPEN_BRACKET("[", 219, Rows.QWE),
    CLOSE_BRACKET("]", 221, Rows.QWE),

    // ASD Row
    A("A", 65, Rows.ASD),
    S("S", 83, Rows.ASD),
    D("D", 68, Rows.ASD),
    F("F", 70, Rows.ASD),
    G("G", 71, Rows.ASD),
    H("H", 72, Rows.ASD),
    J("J", 74, Rows.ASD),
    K("K", 75, Rows.ASD),
    L("L", 76, Rows.ASD),
    SEMI_COLON(";", 186, Rows.ASD),
    SINGLE_QUOTE("'", 222, Rows.ASD),

    // ZXC Row
    Z("Z", 90, Rows.ZXC),
    X("X", 88, Rows.ZXC),
    C("C", 67, Rows.ZXC),
    V("V", 86, Rows.ZXC),
    B("B",66, Rows.ZXC),
    N("N", 78, Rows.ZXC),
    M("M", 77, Rows.ZXC),
    COMMA(",", 188, Rows.ZXC),
    PERIOD(".", 190, Rows.ZXC),
    SLASH("/", 191, Rows.ZXC);

    private final String name;
    private final Integer code;
    private final Integer row;

    Keys(String name, Integer code, Integer row) {
        this.name = name;
        this.code = code;
        this.row = row;
    }

    private final static Set<Integer> keyCodes;
    private final static Set<Integer> rows;

    static {
        keyCodes = new HashSet<>();
        for (Keys key : Keys.values()) {
            keyCodes.add(key.code);
        }

        rows = new HashSet<>();
        rows.add(Rows.NUM);
        rows.add(Rows.QWE);
        rows.add(Rows.ASD);
        rows.add(Rows.ZXC);
    }

    public static boolean keyCodeExists(Integer keyCode) {
        return keyCodes.contains(keyCode);
    }

    public static Boolean keyCodeIsOfRow(Integer keyCode, Integer expectedRow) {
         Optional<Keys> foundKey = Arrays.stream(values())
                .filter(key -> key.code.equals(keyCode))
                .findFirst();
         return foundKey.isPresent() ? foundKey.get().row.equals(expectedRow) : false;
    }

    public static class Rows {
        public static final Integer NUM = 0;
        public static final Integer QWE = 1;
        public static final Integer ASD = 2;
        public static final Integer ZXC = 3;
    }

}
