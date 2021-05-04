package at.htl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        System.out.println("Welcome to Chat Client");
        try {
            Socket clientSocket = new Socket("localhost", 4000);
            Scanner fromServer = new Scanner(clientSocket.getInputStream());
            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);

            //Begrüßungsprotokoll
            String message = fromServer.nextLine();
            System.out.println(message);
            Scanner fromUser = new Scanner(System.in);
            String name = fromUser.nextLine();
            toServer.println(name);
            message = fromServer.nextLine();
            System.out.println(message);

            //Chat
            String text = "";
            while(!text.toLowerCase().equals("x")) {
                text = fromUser.nextLine();
                toServer.println(text);
            }
            //exit chat
            message = fromServer.nextLine();
            System.out.println(message);
            System.exit(0);

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
