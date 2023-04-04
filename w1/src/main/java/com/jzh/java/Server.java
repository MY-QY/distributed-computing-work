package com.jzh.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    private static final Integer MAX_CLIENT_COUNT = 15;
    public static final String SERVER_IP = "127.0.0.1";
    public static final Integer SERVER_PORT = 8998;

    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(MAX_CLIENT_COUNT);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 300, TimeUnit.MILLISECONDS, blockingQueue);
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Started at " + SERVER_IP + ":" + SERVER_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                HandleThread thread = new HandleThread(socket);
                executor.execute(thread);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        executor.shutdown();
    }
}

class HandleThread implements Runnable {
    private final Socket socket;

    public HandleThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                PrintWriter out = new PrintWriter(socket.getOutputStream())
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String message;
            while((message = in.readLine()) != null) {
                System.out.println(Thread.currentThread().getName() + ": receive from port " + socket.getPort() + ": " + message);
//                out.println(message);
                out.write(message);
                out.newLine(); // 如果使用BufferedWriter，则需要添加换行符
                out.flush();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}