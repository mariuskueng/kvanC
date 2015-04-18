package ch.fhnw.kvan.chat.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class ConnectionListener implements Runnable {

    private Logger logger;
    private ServerSocket serverSocket;

    private List<ConnectionHandler> clientList = Collections.synchronizedList(new ArrayList<>());

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
                ConnectionHandler connectionHandler = new ConnectionHandler(s, clientList);
                clientList.add(connectionHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
