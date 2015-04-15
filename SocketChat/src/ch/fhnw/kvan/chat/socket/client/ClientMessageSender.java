package ch.fhnw.kvan.chat.socket.client;

import ch.fhnw.kvan.chat.interfaces.IChatRoom;
import ch.fhnw.kvan.chat.utils.Out;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ClientMessageSender implements IChatRoom {

    private String username;
    private Out outputStream;
    private Logger logger;

    public ClientMessageSender(String u, Out o) {
        this.username = u;
        this.outputStream = o;
        logger = Logger.getLogger(ClientMessageSender.class);
        logger.info("New client sender");
    }

    @Override
    public boolean addParticipant(String name) throws IOException {
        outputStream.println("name=" + name);
        return true;
    }

    @Override
    public boolean removeParticipant(String name) throws IOException {
        outputStream.println("remove_name=" + name);
        return true;
    }

    @Override
    public boolean addTopic(String topic) throws IOException {
        outputStream.println("add_topic=" + topic);
        return true;
    }

    @Override
    public boolean removeTopic(String topic) throws IOException {
        outputStream.println("remove_topic=" + topic);
        return true;
    }

    @Override
    public boolean addMessage(String topic, String message) throws IOException {
        outputStream.println("message=" + message + ";topic="+topic);
        return true;
    }

    @Override
    public String getMessages(String topic) throws IOException {
        outputStream.println("action=getMessages;topic=" + topic);
        return null;
    }

    @Override
    public String refresh(String topic) throws IOException {
        outputStream.println("action=refreshMessages;topic=" + topic);
        return null;
    }
}
