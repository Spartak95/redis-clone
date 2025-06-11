package com.xcoder.redis.clone.handler;

import com.xcoder.redis.clone.common.CommandData;
import com.xcoder.redis.clone.exception.ProcessException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class CommandHandler extends AbstractHandler implements Runnable {
    private final Socket clientSocket;

    public CommandHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    protected void processResponse(byte[] response, CommandData commandData, DataInputStream inputStream, OutputStream outputStream) throws IOException {
        outputStream.write(response);
        outputStream.flush();
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            OutputStream outputStream = clientSocket.getOutputStream();
            handle(inputStream, outputStream);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (ProcessException e) {
            System.out.println("ProcessException: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
