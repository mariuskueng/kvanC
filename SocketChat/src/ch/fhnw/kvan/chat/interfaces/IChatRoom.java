/*
 * Copyright (c) 2012 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */
package ch.fhnw.kvan.chat.interfaces;

import java.io.IOException;

/**
 * The ChatRoom interface defines the functionality of a chat room server. The
 * chat room can add/remove topics and participants, receives and saves messages, 
 * gets the last ten messages on a given topic or gets the information for a complete refresh; 
 * i.e.gets the last ten messages on a given topic as well as the current lists of participants and 
 * topics from the chat room.
 * 
 * @see IChatRoom
 * @author © ibneco, Rheinfelden
 * @version
 */
public interface IChatRoom {
	
	/**
	 * Add a participant who joined the chat room.
	 * 
	 * @param name
	 *            The name of the participant
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public boolean addParticipant(String name) throws IOException;

	/**
	 * Remove a participant who left the chat room.
	 * 
	 * @param name
	 *            The name of the participant
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public boolean removeParticipant(String name) throws IOException;

	/**
	 * Add a topic to the chat room.
	 * 
	 * @param name
	 *            The string defining the topic
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public boolean addTopic(String topic) throws IOException;

	/**
	 * Remove a topic from the chat room.
	 * 
	 * @param name
	 *            The string defining the topic
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public boolean removeTopic(String topic) throws IOException;
	
	/**
	 * Saves/Sends a message to the chat room.
	 * Server saves the message; Client sends the message.
	 * 
	 * @param topic
	 *            The string defining the topic
	 * @param message
	 *            The message
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public boolean addMessage(String topic, String message) throws IOException;

	/**
	 * Get last ten messages from the chat room.
	 * 
	 * @param topic
	 *            The string defining the topic
	 * @returns String
	 *            Last ten messages on that topic
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public String getMessages(String topic) throws IOException;
	
	/**
	 * Refresh last ten messages from the chat room; refresh participants and topics, too.
	 * 
	 * @param topic
	 *            The string defining the current topic
	 * @returns String
	 *            Last ten messages on that topic
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public String refresh(String topic) throws IOException;

}
