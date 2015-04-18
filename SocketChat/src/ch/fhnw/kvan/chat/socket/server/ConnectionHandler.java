package ch.fhnw.kvan.chat.socket.server;

import ch.fhnw.kvan.chat.general.ChatRoom;
import ch.fhnw.kvan.chat.utils.In;
import ch.fhnw.kvan.chat.utils.Out;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ConnectionHandler implements Runnable {

    private Logger logger = Logger.getLogger(Server.class);
    private List<ConnectionHandler> connections;

    private In inputStream;
    private Out outputStream;

    private ChatRoom chat = ChatRoom.getInstance();

    public ConnectionHandler(Socket socket, List<ConnectionHandler> connections) {
        logger.setLevel(Level.ALL);
        this.connections = connections;
        this.outputStream = new Out(socket);
        this.inputStream = new In(socket);

        Thread connectionListener = new Thread(this);
        connectionListener.start();
    }

    @Override
    public void run() {

        while(true) {
            String clientMessage = inputStream.readLine();

            if (clientMessage == null) {
                logger.error("There are no clients to connect");
                break;
            }

            try {
                processMessage(clientMessage);
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    public void processMessage(String message) throws IOException {
        String action = message.split("=")[0];
        String content = message.split("=")[1];

        switch (action) {
            case "name":
                chat.addParticipant(content);
                break;

            case "remove_name":
                chat.removeParticipant(content);
                break;

            case "add_topic":
                chat.addTopic(content);
                break;

            case "remove_topic":
                chat.removeTopic(content);
                break;

            case "message":
                System.out.println(content);
                chat.addMessage("", content);
                break;

            case "get_topics":
                chat.getTopics();
                break;

            case "get_participants":
                chat.getParticipants();
                break;
        }

        logger.info("Notify all clients -> Message : " + message);

        for (ConnectionHandler each : connections) {
            each.outputStream.println(message);
        }
    }
}
