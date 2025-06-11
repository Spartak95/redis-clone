package com.xcoder.redis.clone.command;

import java.util.Arrays;

public enum CommandType {
    PING("ping");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandType fromCommand(String currentCommand) {
        return Arrays.stream(values())
                .filter(it -> it.command.equalsIgnoreCase(currentCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Command not found! " + currentCommand));
    }
}
