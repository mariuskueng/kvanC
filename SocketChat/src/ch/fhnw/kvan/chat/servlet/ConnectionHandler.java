package ch.fhnw.kvan.chat.servlet;

import ch.fhnw.kvan.chat.general.ChatRoom;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ConnectionHandler {
    private static Logger logger = Logger.getLogger(ConnectionHandler.class);
    private ChatRoom chatRoom;

    public ConnectionHandler(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void addParticipant() throws IOException {

    }

}
