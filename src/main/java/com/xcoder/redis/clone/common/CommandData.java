package com.xcoder.redis.clone.common;

import com.xcoder.redis.clone.command.CommandType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class CommandData {
    private final String command;
    private final Long commandLength;
    private List<String> parts;

    private CommandData(String command, Long commandLength) {
        this.command = command;
        this.commandLength = commandLength;
    }

    public static CommandData of(String command, Long commandLength) {
        return new CommandData(command, commandLength);
    }

    public String getCommand() {
        return command;
    }

    public Long getCommandLength() {
        return commandLength;
    }

    public List<String> getParts() {
        if (parts == null) {
            parts = Arrays.stream(command.split(" ")).map(String::trim).toList();
        }
        return parts;
    }

    public String getPart(int index) {
        return getParts().get(index);
    }

    public String getAllParts(int from) {
        return String.join(" ", getParts().subList(from, getParts().size()));
    }

    public <T> T getPart(int index, Function<String, T> converter) {
        try {
            return converter.apply(getPart(index));
        } catch (Exception e) {
            throw new IllegalArgumentException("Exception trying to convert part of command: " + e.getMessage());
        }
    }

    public Pair<String, Integer> getPartByName(String name) {
        return getPartByName(name, String::valueOf);
    }

    public <T> Pair<T, Integer> getPartByName(String name, Function<String, T> converter) {
        for (int i = 0; i < getParts().size(); i++) {
            String part = parts.get(i);
            if (part.equalsIgnoreCase(name)) {
                return Pair.of(converter.apply(part), i);
            }
        }

        return Pair.of(null, -1);
    }

    public Integer partsSIze() {
        return getParts().size();
    }

    public CommandType extractCommandType() {
        return CommandType.fromCommand(getPart(0));
    }

    public <T> T convertCommand(Function<String, T> converter) {
        return converter.apply(command);
    }
}
