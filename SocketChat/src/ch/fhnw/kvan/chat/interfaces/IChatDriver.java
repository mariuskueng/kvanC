/*
 * Copyright (c) 2012 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.kvan.chat.interfaces;

import java.io.IOException;

/**
 * The ChatDriver interface is used to access a particular chat room. The client
 * program first calls connect over this interface and then requests the chat
 * room.
 * 
 * @see IChatRoom
 * @author © ibneco, Rheinfelden
 * @version
 */

public interface IChatDriver {

	/**
	 * Connects to an implementation of a chat room.
	 * 
	 * @param host
	 *            host name
	 * @param port
	 *            port number
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public void connect(String host, int port) throws IOException;

	/**
	 * Disconnects from the chat room server .
	 * 
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public void disconnect() throws IOException;

	/**
	 * Return the chat room which is operated by that server. Before getChatRoom
	 * can be invoked, connect must be called. getChatRoom must have singleton
	 * semantics, i.e. it should return the same instance upon subsequent calls.
	 * After disconnect has been called getChatRoom returns null.
	 * 
	 * @see #connect
	 * @see Chat
	 */
	public IChatRoom getChatRoom();

}
