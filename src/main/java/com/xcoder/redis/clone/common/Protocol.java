package com.xcoder.redis.clone.common;

public enum Protocol {
    ARRAY("*"),
    BULD_STRING("$"),
    SIMPLE_STRING("+"),
    INTEGER(":"),
    ERROR("-");

    private final String symbol;

    Protocol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
