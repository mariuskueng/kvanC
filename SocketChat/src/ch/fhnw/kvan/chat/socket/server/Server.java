package ch.fhnw.kvan.chat.socket.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Server {

	private static final int SERVER_PORT = 1235;

	public static void main (String[] args) {

		BasicConfigurator.configure();
		Logger logger = Logger.getLogger(Server.class);

		try {
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			ConnectionListener connectionListener = new ConnectionListener(serverSocket);

			logger.info("New connectionListener instance on port " + SERVER_PORT);

			Thread serverConnectionThread = new Thread(connectionListener);
			serverConnectionThread.start();

		} catch (IOException e) {
			System.out.println("Could not listen on port " + SERVER_PORT);
			e.printStackTrace();
		}

	}
}
