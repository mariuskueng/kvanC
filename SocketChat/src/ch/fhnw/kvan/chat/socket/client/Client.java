package ch.fhnw.kvan.chat.socket.client;

import java.io.IOException;

import ch.fhnw.kvan.chat.gui.ClientGUI;
import ch.fhnw.kvan.chat.socket.server.Server;

public class Client {
	private String name;
	private String host;
	private int port;

	public static void main(String[] args) throws IOException {
		System.out.println("Client instance started!");
		
		if (args.length != 3) {
			throw new IOException("More or less args given.");
		}
		
		try {
			Client client = new Client(args[0], args[1], Integer.parseInt(args[2]));
			
			System.out.println(client.getName());
			System.out.println(client.getHost());
			System.out.println(client.getPort());
			
			Server chatRoom = new Server();
			chatRoom.connect(client.getHost(), client.getPort());
			ClientGUI gui = new ClientGUI(chatRoom, client.name);
			
		} catch (Exception e) {
			throw new IOException("Missing arguments in client constructor.");
		}
		
	}
	
	public Client (String n, String h, int p) {
		this.name = n;
		this.host = h;
		this.port = p;
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
