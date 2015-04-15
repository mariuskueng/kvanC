package ch.fhnw.kvan.chat.socket.client;

import ch.fhnw.kvan.chat.interfaces.IChatRoom;
import ch.fhnw.kvan.chat.utils.Out;

import java.io.IOException;

public class ClientMessageSender implements IChatRoom {

    private String username;
    private Out outputStream;

    public ClientMessageSender(String u, Out o) {
        this.username = u;
        this.outputStream = o;
    }

    @Override
    public boolean addParticipant(String name) throws IOException {
        return false;
    }

    @Override
    public boolean removeParticipant(String name) throws IOException {
        return false;
    }

    @Override
    public boolean addTopic(String topic) throws IOException {
        return false;
    }

    @Override
    public boolean removeTopic(String topic) throws IOException {
        return false;
    }

    @Override
    public boolean addMessage(String topic, String message) throws IOException {
        return false;
    }

    @Override
    public String getMessages(String topic) throws IOException {
        return null;
    }

    @Override
    public String refresh(String topic) throws IOException {
        return null;
    }
}
