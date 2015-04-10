package ch.fhnw.kvan.chat.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ConnectionListener implements Runnable {

    private Logger logger;
    private ServerSocket serverSocket;

    private List<ConnectionListener> clientList = new ArrayList<>();

    public ConnectionListener(ServerSocket s) {
        this.serverSocket = s;
        logger = Logger.getLogger(ConnectionListener.class);
    }

    @Override
    public void run() {
        while (true) {
            Socket s;
            logger.info("ConnectionListener started...");
            try {
                s = serverSocket.accept();
                System.out.println("End serving " + s);
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
