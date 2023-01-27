package Main;

import server.Server;

import java.io.IOException;

import static Database.SessionFactoryImpl.getSessionFactory;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        getSessionFactory();
        server.startServer();
    }
}