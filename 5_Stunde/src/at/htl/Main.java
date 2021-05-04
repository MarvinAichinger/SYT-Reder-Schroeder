package at.htl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome at the Chat Server");

        try {
            ServerSocket chatServer = new ServerSocket(4000);
            while(true) {
                Socket client = chatServer.accept();

                Thread t = new Thread(() -> {
                    try {
                        System.out.println("Client connected on address " + client.getRemoteSocketAddress().toString());
                        PrintWriter outToClient = new PrintWriter(client.getOutputStream(), true);
                        outToClient.println("Griasdi, wer bist denn du?");
                        Scanner inFromClient = new Scanner(client.getInputStream());
                        String name = inFromClient.nextLine();
                        outToClient.println("Nice to see you, " + name + "! Please start the chat. Press 'x' to exit the chat.");
                        String message = inFromClient.nextLine();
                        while (!message.toLowerCase().equals("x")) {
                            System.out.println(name + " > " + message);
                            message = inFromClient.nextLine();
                        }
                        outToClient.println("Servas!");
                        client.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
