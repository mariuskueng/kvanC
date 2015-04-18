package ch.fhnw.kvan.chat.socket.server;

import ch.fhnw.kvan.chat.utils.In;
import ch.fhnw.kvan.chat.utils.Out;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.List;

public class ConnectionHandler implements Runnable {

    private Logger logger = Logger.getLogger(Server.class);
    private List<ConnectionHandler> connections;

    private In inputStream;
    private Out outputStream;

    public ConnectionHandler(Socket socket, List<ConnectionHandler> connections) {
        this.connections = connections;
        this.outputStream = new Out(socket);
        this.inputStream = new In(socket);

        Thread connectionListener = new Thread(this);
        connectionListener.start();
    }

    @Override
    public void run() {

        while(true) {
            System.out.println(inputStream.readLine());
        }
    }
}
