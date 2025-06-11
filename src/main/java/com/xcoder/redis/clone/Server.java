package com.xcoder.redis.clone;

import com.xcoder.redis.clone.exception.ServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            System.out.println("Server started, listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new CommandHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }
}
