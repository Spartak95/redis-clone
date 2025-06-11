package com.xcoder.redis.clone.command.context;

import com.xcoder.redis.clone.common.CommandData;

public class PingCommandContext extends AbstractCommandContext {

    public PingCommandContext(CommandData commandData) {
        super(commandData);
    }

    @Override
    public byte[] buildResponse() {
        return writer.simpleString("PONG");
    }
}
