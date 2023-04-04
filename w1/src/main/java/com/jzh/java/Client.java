package com.jzh.java;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                Socket socket = new Socket(Server.SERVER_IP, Server.SERVER_PORT)
        ) {
            System.out.println("Connected to server: " + Server.SERVER_IP + ":" + Server.SERVER_PORT);
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
//                    PrintWriter out = new PrintWriter(socket.getOutputStream())
            ) {
                String message;
                while((message = stdIn.readLine()) != null) {
//                    out.println(message);
                    out.write(message);
                    out.newLine(); // 如果使用BufferedWriter，则需要添加换行符
                    out.flush();
                    System.out.println("Receive from server: " + in.readLine());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
