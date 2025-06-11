package com.xcoder.redis.clone.io;

import com.xcoder.redis.clone.command.Command;
import com.xcoder.redis.clone.common.Protocol;

public class Writer {

    public byte[] bulkString(String value) {
        if (value == null) {
            return (Protocol.BULD_STRING.getSymbol() + "-1" + Command.CRLF).getBytes();
        }

        return (Protocol.BULD_STRING.getSymbol() + value.length() + value + Command.CRLF).getBytes();
    }

    public byte[] simpleString(String value) {
        return (Protocol.SIMPLE_STRING.getSymbol() + value + Command.CRLF).getBytes();
    }

    public byte[] simpleError(String value) {
        return (Protocol.ERROR.getSymbol() + value + Command.CRLF).getBytes();
    }

    public byte[] integer(String value) {
        return (Protocol.INTEGER.getSymbol() + value + Command.CRLF).getBytes();
    }
}
