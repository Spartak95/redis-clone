package com.xcoder.redis.clone.command.context;

import com.xcoder.redis.clone.common.CommandData;
import com.xcoder.redis.clone.common.ObjectHolder;
import com.xcoder.redis.clone.io.Writer;

public abstract class AbstractCommandContext implements CommandContext {
    protected CommandData commandData;
    protected final Writer writer;

    protected AbstractCommandContext(CommandData commandData) {
        this.writer = ObjectHolder.getInstance().getObject(Writer.class);
        this.commandData = commandData;
    }

    @Override
    public byte[] buildResponse() {
        return new byte[0];
    }
}
