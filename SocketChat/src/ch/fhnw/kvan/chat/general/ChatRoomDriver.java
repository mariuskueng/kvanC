package ch.fhnw.kvan.chat.general;

import org.apache.log4j.Logger;

import ch.fhnw.kvan.chat.interfaces.IChatDriver;
import ch.fhnw.kvan.chat.interfaces.IChatRoom;

/**
 * The ChatRoomDriver provides access to a particular chat room. The Server has
 * to call connect() to create a single instance of the ChatRoom class and then
 * only gets a reference to it.
 * 
 * @see ChatRoomDriver
 * @author © ibneco, Rheinfelden
 * @version
 */
public class ChatRoomDriver implements IChatDriver {
	private ChatRoom chatRoom = null;

	private static Logger logger;

	public ChatRoomDriver() {
		// Log4J initialisation
		logger = Logger.getLogger(ChatRoomDriver.class);
	}

	@Override
	public void connect(String host, int port) {
		chatRoom = ChatRoom.getInstance();
		logger.info("connected...");
	}

	@Override
	public void disconnect() {
		chatRoom = null;
		logger.info("disconnected...");
	}

	@Override
	public IChatRoom getChatRoom() {
		return chatRoom;
	}

}