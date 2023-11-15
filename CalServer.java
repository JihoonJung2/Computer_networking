package hw1;

import java.io.*;
import java.net.*;
import java.util.*;


public class CalServer {

	public static String calc(String exp) {
	    StringTokenizer st = new StringTokenizer(exp, " ");
	    if(st.countTokens() ==2)return "HTTP/1.1 400 Bad Request Error message: Too few arguments";
	    else if (st.countTokens() != 3)
	        return "HTTP/1.1 400 Bad Request Error message: Too many arguments";

	    String opcode = st.nextToken();
	    double op1, op2;
	    try {
	        op1 = Double.parseDouble(st.nextToken());
	        op2 = Double.parseDouble(st.nextToken());
	    } catch (NumberFormatException e) {
	        return "HTTP/1.1 400 Bad Request Error message: Invalid Operand type";
	    }

	    try {
	        switch (opcode) {
	            case "ADD":
	                return "HTTP/1.1 200 OK Result: " + op1+ " + " + op2 + " = " + Double.toString(op1 + op2);
	            case "MIN":
	                return "HTTP/1.1 200 OK Result: " + op1+ " - " + op2 + " = " + Double.toString(op1 - op2);
	            case "MUL":
	                return "HTTP/1.1 200 OK Result: " + op1+ " x " + op2 + " = " + Double.toString(op1 * op2);
	            case "DIV":
	                if (op2 != 0) {
	                    return "HTTP/1.1 200 OK Result: " + op1+ " / " + op2 + " = " + Double.toString(op1 / op2);
	                } else {
	                    return "HTTP/1.1 400 Bad Request Error message: Division by zero";
	                }
	            default:
	                return "HTTP/1.1 400 Bad Request Error message: Invalid command: " + opcode;
	        }
	    } catch (Exception e) {
	        return "HTTP/1.1 500 Internal Server Error Error message: " + e.getMessage();
	    }
	}


    public static void main(String[] args) {
        BufferedReader in = null;
        BufferedWriter out = null;
        ServerSocket listener = null;
        Socket socket = null;

        try {
            listener = new ServerSocket(9999); // create server socket
            System.out.println("Waiting for connection.....");
            socket = listener.accept(); // waiting for connection request from the client
            System.out.println("Connected.");
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                String inputMessage = in.readLine();
                if (inputMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Client has closed the connection");
                    break; // if receive bye, end
                }
                System.out.println(inputMessage); // print the result
                String res = calc(inputMessage); 
                out.write(res + "\n"); // send result
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null)
                    socket.close(); // close the communication socket
                if (listener != null)
                    listener.close(); // close the server socket
            } catch (IOException e) {
                System.out.println("An error occurred during communication with the client.");
            }
        }
    }
}
