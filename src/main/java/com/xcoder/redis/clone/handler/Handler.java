package com.xcoder.redis.clone.handler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface Handler {
    void handle(DataInputStream inputStream, OutputStream outputStream) throws IOException;
}
