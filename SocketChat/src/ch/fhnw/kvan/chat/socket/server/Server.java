package ch.fhnw.kvan.chat.socket.server;

import java.io.IOException;

import ch.fhnw.kvan.chat.interfaces.IChatDriver;
import ch.fhnw.kvan.chat.interfaces.IChatRoom;

public class Server implements IChatRoom, IChatDriver{

	@Override
	public void connect(String host, int port) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IChatRoom getChatRoom() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean addParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMessage(String topic, String message) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMessages(String topic) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String refresh(String topic) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
