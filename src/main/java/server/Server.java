package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public final class Server {

    private ServerSocket socket = null;
    private Vector<Socket> clients = null;

    public void startServer() throws IOException{
        try {
            clients = new Vector<Socket>();
            socket = new ServerSocket(8989);
            System.out.println(">>TCP server created. Address: " + socket.getInetAddress() + "\nPort: " + socket.getLocalPort());
            //socket.setReuseAddress(true);

            while (true) {

                Socket clientSocket = socket.accept();
                clients.add(clientSocket);

                System.out.println(">>New client connected: " + clientSocket.getInetAddress());

                // create a new thread object
                ClientHandler newClient = new ClientHandler(clientSocket);

                new Thread(newClient).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(clients != null){
                clients.clear();
            }
        }
    }

}
