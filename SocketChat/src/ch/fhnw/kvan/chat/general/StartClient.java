/*
 * Copyright (c) 2000-2009 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.kvan.chat.general;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import ch.fhnw.kvan.chat.socket.client.Client;

/**
 * Class StartClient is used to start the Client side of the chat application. As a
 * runtime parameter the name of the class which implements the
 * <code>Client</code> class has to be specified. This class is then
 * loaded and its main() method invoked, using corresponding arguments.
 * 
 * E.g. start the application with one of the following commands, which are defined in the Clients.txt file. 
 * 
 * <pre>
 * ch.fhnw.kvan.chat.socket.client.Client localhost 1234 <user name>
 * ch.fhnw.kvan.chat.servlet.Client localhost:8080/chat/Server <user name>
 * </pre>
 * 
 * @see Client 
 * @author  © ibneco, Rheinfelden; based on code by Dominik Gruntz
 * @version
 */
public class StartClient {
	
	private static Logger logger;

	public static void main(String args[]) {
		// Log4J initialisation
		logger = Logger.getLogger(StartClient.class);
		// Set up a simple configuration that logs on the console
		BasicConfigurator.configure();
				
		if (args.length < 1) {
			logger.error("Usage: java " + StartClient.class.getName()
					+ " <class>");
			System.exit(1);
		}

        try {
        	// get class for name
			Class<?> cls = Class.forName(args[0]);
			// get its main method
			Method meth = cls.getDeclaredMethod("main", String[].class);
			// the different Client classes need different arguments
			// communication via socket: package name contains "socket"
			if (args[0].contains("socket")) {
				// prepare right parameters
				String[] params = {args[3], args[1], args[2]}; 
				// invoke main()
				meth.invoke(null, (Object) params);
			}
			// communication via servlet: package name contains "servlet"
			else if (args[0].contains("servlet")) {
				// prepare right parameters
				String[] params = {args[2], args[1]}; 
				// invoke main()
				meth.invoke(null, (Object) params);
				} 		    
		} catch (ClassNotFoundException e) {
			logger.error("class " + args[0] + " could not be found");
			System.exit(1);
		} catch (NoSuchMethodException e) {
			logger.error("class " + args[0]
					+ " does not have such method");
			System.exit(1);
		} catch (InvocationTargetException e) {
			logger.error("main method of class " + args[0]
					+ " threw an InvocationTargetException: " + e.getCause());
			System.exit(1);
		} catch (IllegalAccessException e) {
			logger.error("class " + args[0]
					+ " could not be instantiated");
			System.exit(1);
		} catch (IllegalArgumentException e) {
			logger.error("class " + args[0]
					+ " could not invoke the main method");
			System.exit(1);
		}
	}
}