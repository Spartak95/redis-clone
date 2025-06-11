package com.xcoder.redis.clone.command.processor;

import com.xcoder.redis.clone.command.Command;
import com.xcoder.redis.clone.command.CommandType;
import com.xcoder.redis.clone.command.PingCommand;
import com.xcoder.redis.clone.command.context.CommandContext;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandProcessor {
    private final Map<CommandType, Command<?>> commands;

    public CommandProcessor() {
        commands = new ConcurrentHashMap<>();
        commands.put(CommandType.PING, new PingCommand());
    }

    @SuppressWarnings("unchecked")
    public <T extends CommandContext> byte[] process(T context, CommandType commandType) throws IOException {
        Command<T> command = (Command<T>) commands.get(commandType);

        if (command != null) {
            return command.execute(context);
        } else {
            throw new IllegalArgumentException("Unknown command type: " + commandType);
        }
    }
}
