package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private int currentClients = 0;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try{
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            currentClients++;
            printCurrentClients();

            ClientMenu clientMenu = new ClientMenu(inputStream, outputStream);
            clientMenu.startClientMenu();


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void printCurrentClients() {
        System.out.println(">>current number of clients: " + currentClients);
    }

    public int getCurrentClients() {
        return currentClients;
    }

    public void setCurrentClients(int currentClients) {
        this.currentClients = currentClients;
    }
}

