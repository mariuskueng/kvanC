package ch.fhnw.kvan.chat.socket.client;

import java.io.IOException;
import java.net.Socket;

import ch.fhnw.kvan.chat.gui.ClientGUI;
import ch.fhnw.kvan.chat.utils.Out;
import org.apache.log4j.Logger;

public class Client {
	private String name;
	private String host;
	private int port;
	private ClientGUI gui;
	private ClientMessageSender chatRoom;

	private Out outputStream;
	private Socket socket;

	public static void main(String[] args) throws IOException {
		Logger logger = Logger.getLogger(ClientMessageSender.class);
		logger.info("Client instance started");
		
		if (args.length != 3) {
			throw new IOException("More or less args given.");
		}
		
		try {
			Client client = new Client(args[0], args[1], Integer.parseInt(args[2]));

		} catch (Exception e) {
			throw new IOException("Missing arguments in client constructor.");
		}
		
	}
	
	public Client (String n, String h, int p) {
		this.name = n;
		this.host = h;
		this.port = p;

		try {
			this.socket = new Socket(this.host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.outputStream = new Out(this.socket);
		this.chatRoom = new ClientMessageSender(this.name, this.outputStream);

		try {
			chatRoom.addParticipant(this.name);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ClientGUI gui = new ClientGUI(chatRoom, this.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
