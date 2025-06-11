package com.xcoder.redis.clone.handler;

import com.xcoder.redis.clone.command.CommandType;
import com.xcoder.redis.clone.command.context.CommandContext;
import com.xcoder.redis.clone.command.processor.CommandProcessor;
import com.xcoder.redis.clone.common.CommandData;
import com.xcoder.redis.clone.common.ObjectHolder;
import com.xcoder.redis.clone.io.Reader;
import com.xcoder.redis.clone.io.Writer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractHandler implements Handler {
    protected final CommandProcessor commandProcessor;
    protected final Reader reader;
    protected final Writer writer;
    private final Map<CommandType, Function<CommandData, CommandContext>> contexts = new HashMap<>();

    public AbstractHandler() {
        this.commandProcessor = new CommandProcessor();
        this.reader = ObjectHolder.getInstance().getObject(Reader.class);
        this.writer = ObjectHolder.getInstance().getObject(Writer.class);
    }
}
