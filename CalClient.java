package hw1;

import java.io.*;
import java.net.*;
import java.util.*;

public class CalClient {
    public static void main(String[] args) {
        BufferedReader in = null;
        BufferedWriter out = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 9999);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                System.out.print("Enter ADD, MIN, MUL, DIV + integer( ex) ADD 10 20)>>"); 
                String outputMessage = scanner.nextLine(); // scan
                if (outputMessage.equalsIgnoreCase("bye")) {
                    out.write(outputMessage + "\n"); // print bye
                    out.flush();
                    break; // if scan bye, end
                }
                out.write(outputMessage + "\n"); // send string
                out.flush();
                String inputMessage = in.readLine(); // receive result
                System.out.println(inputMessage);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                scanner.close();
                if (socket != null)
                    socket.close(); // close socket
            } catch (IOException e) {
                System.out.println("\"An error occurred during communication with the client");
            }
        }
    }
}
