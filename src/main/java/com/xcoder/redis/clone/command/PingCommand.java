package com.xcoder.redis.clone.command;

import com.xcoder.redis.clone.command.context.PingCommandContext;

import java.io.IOException;

public class PingCommand implements Command<PingCommandContext> {

    @Override
    public byte[] execute(PingCommandContext context) throws IOException {
        return context.buildResponse();
    }
}
