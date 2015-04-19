package ch.fhnw.kvan.chat.socket.client;

import ch.fhnw.kvan.chat.gui.ClientGUI;
import ch.fhnw.kvan.chat.utils.In;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ClientMessageReceiver implements Runnable {

    private In inputStream;
    private ClientGUI gui;
    private Logger logger = Logger.getLogger(Client.class);

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
        String content;

        try {
            content = message.split("=")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            content = "";
        }

        if (action.contains("action")) {
            action = content;
        }

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
                gui.addMessage(message.split(";")[0].split("=")[1]);
                break;

            case "get_topics":
                String[] topics;
                try {
                    topics = message.split("=")[1].split(";");
                } catch (ArrayIndexOutOfBoundsException e){
                    topics = null;
                }
                gui.updateTopics(topics);
                break;

            case "get_participants":
                gui.updateParticipants(content.split(";"));
                break;

            case "get_messages":
                gui.updateMessages(content.split(";")[1].split("="));
                break;
        }
    }
}
