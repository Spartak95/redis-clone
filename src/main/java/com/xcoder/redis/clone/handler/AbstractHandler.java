package com.xcoder.redis.clone.handler;

import com.xcoder.redis.clone.command.CommandType;
import com.xcoder.redis.clone.command.context.CommandContext;
import com.xcoder.redis.clone.command.context.PingCommandContext;
import com.xcoder.redis.clone.command.processor.CommandProcessor;
import com.xcoder.redis.clone.common.CommandData;
import com.xcoder.redis.clone.common.ObjectHolder;
import com.xcoder.redis.clone.exception.ProcessException;
import com.xcoder.redis.clone.io.Reader;
import com.xcoder.redis.clone.io.Writer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractHandler implements Handler {
    protected final CommandProcessor commandProcessor;
    protected final Reader reader;
    protected final Writer writer;
    private final Map<CommandType, Function<CommandData, CommandContext>> contexts = new HashMap<>();

    protected AbstractHandler() {
        this.commandProcessor = new CommandProcessor();
        this.reader = ObjectHolder.getInstance().getObject(Reader.class);
        this.writer = ObjectHolder.getInstance().getObject(Writer.class);

        contexts.put(CommandType.PING, PingCommandContext::new);
    }

    @Override
    public void handle(DataInputStream inputStream, OutputStream outputStream) throws IOException {
        CommandData commandData = reader.read(inputStream);
        CommandType commandType = commandData.extractCommandType();
        CommandContext context = contexts.get(commandType).apply(commandData);

        Supplier<byte[]> command = () -> {
            try {
                return commandProcessor.process(context, commandType);
            } catch (IOException e) {
                throw new ProcessException(e);
            }
        };

        processResponse(command.get(), commandData, inputStream, outputStream);
    }

    protected abstract void processResponse(byte[] response,
                                            CommandData commandData,
                                            DataInputStream inputStream,
                                            OutputStream outputStream) throws IOException;
}
