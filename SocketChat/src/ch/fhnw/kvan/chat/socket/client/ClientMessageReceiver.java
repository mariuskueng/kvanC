package ch.fhnw.kvan.chat.socket.client;

import ch.fhnw.kvan.chat.gui.ClientGUI;
import ch.fhnw.kvan.chat.utils.In;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ClientMessageReceiver implements Runnable {

    private In inputStream;
    private ClientGUI gui;
    private Logger logger;

    public ClientMessageReceiver (In inputStream, ClientGUI gui) {
        this.inputStream = inputStream;
        this.gui = gui;

        Thread serverListener = new Thread(this);
        serverListener.start();
    }

    @Override
    public void run() {
        while (true) {
            String message = this.inputStream.readLine();
            logger.info("Message from server received: " + message);
            try {
                processMessage(message);
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
                gui.addParticipant(content);
                break;

            case "remove_name":
                gui.removeParticipant(content);
                break;

            case "add_topic":
                gui.addTopic(content);
                break;

            case "remove_topic":
                gui.removeTopic(content);
                break;

            case "message":
                System.out.println(content);
                gui.addMessage(content);
                break;

            case "get_topics":
                gui.updateTopics(content.split(";"));
                break;

            case "get_participants":
                gui.updateParticipants(content.split(";"));
                break;
        }
    }
}
