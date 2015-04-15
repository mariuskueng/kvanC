package ch.fhnw.kvan.chat.socket.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

public class Server {

	private Logger logger;
	private ServerSocket serverSocket;

	public Server() {
		logger = Logger.getLogger(Server.class);
	}

	public static void main (String[] args) {

		try{
			serverSocket = new ServerSocket(port);
			ConnectionListener connectionListener = new ConnectionListener(serverSocket);
			logger.info("New connectionListener instance on port " + port);

			Thread serverConnectionThread = new Thread(connectionListener);
			serverConnectionThread.start();

		} catch (IOException e) {
			System.out.println("Could not listen on port " + port);
		}

	}
}
