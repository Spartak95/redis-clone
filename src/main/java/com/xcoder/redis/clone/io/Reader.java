package com.xcoder.redis.clone.io;

import com.xcoder.redis.clone.common.CommandData;
import com.xcoder.redis.clone.common.Protocol;
import com.xcoder.redis.clone.exception.ReaderException;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class Reader {

    public CommandData read(DataInputStream inputStream) {
        try {
            char c = (char) inputStream.readByte();

            var parserResult = switch (c) {
                case '*' -> readArray(inputStream);
                case '$' -> readBulkString(inputStream);
                case '+' -> readSimpleString(inputStream);
                default -> throw new ReaderException("Unknown character '" + c + "'");
            };

            return CommandData.of(parserResult.getCommand().trim(),
                    parserResult.getCommandLength() + 1);
        } catch (EOFException e) {
            throw new ReaderException(e);
        } catch (IOException e) {
            throw new ReaderException(e);
        }
    }

    private CommandData readArray(DataInputStream inputStream) throws IOException {
        CommandData arrayLength = readSimpleString(inputStream);
        int length = arrayLength.convertCommand(Integer::valueOf);
        StringBuilder commandBuilder = new StringBuilder();
        Long totalLength = arrayLength.getCommandLength();

        for (int i = 0; i < length; i++) {
            CommandData parsedCommand = read(inputStream);
            commandBuilder.append(parsedCommand.getCommand()).append(" ");
            totalLength += parsedCommand.getCommandLength();
        }

        return CommandData.of(commandBuilder.toString().trim(), totalLength);
    }

    private CommandData readBulkString(DataInputStream inputStream) throws IOException {
        CommandData parsedResult = readSimpleString(inputStream);
        int stringLength= parsedResult.convertCommand(Integer::valueOf);

        StringBuilder stringBuilder = new StringBuilder();
        long commandLength = parsedResult.getCommandLength();

        for (int i = 0; i < stringLength; i++) {
            stringBuilder.append((char) inputStream.readByte());
            commandLength++;
        }

        inputStream.skip(2);
        commandLength += 2;

        return CommandData.of(stringBuilder.toString(), commandLength);
    }

    private CommandData readSimpleString(DataInputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        long commandLength = 1L;
        int b;

        while ((b = inputStream.readByte()) != '\r') {
            buffer.write(b);
            commandLength++;
        }

        inputStream.readByte();
        commandLength++;

        return CommandData.of(buffer.toString().trim(), commandLength);
    }

    public String readFully(DataInputStream inputStream) throws IOException {
        char c = (char) inputStream.readByte();
        if (c != Protocol.BULD_STRING.getSymbol().charAt(0)) {
            throw new ReaderException("Unexpected character. Expected '$'. Get '" + c + "'");
        }

        int stringLength = readSimpleString(inputStream).convertCommand(Integer::valueOf);
        byte[] buffer = new  byte[stringLength];
        inputStream.readFully(buffer);

        return new String(buffer);
    }
}
