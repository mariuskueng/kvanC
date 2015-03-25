/*
 * Copyright (c) 2012 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.kvan.chat.interfaces;


public interface IChats {

         /**
	 * Add a topic to the topic list and topic string.
	 * 
	 * @param name
	 *            The string defining the topic
	 */
	public boolean addTopic(String topic);

	/**
	 * Remove a topic from the topic list and topic string.
	 * 
	 * @param name
	 *            The string defining the topic
	 * @throws IOException
	 *             if a remote or communication problem occurs
	 */
	public boolean removeTopic(String topic);
	
	/**
	 * Get topic string.
	 *
	 * @returns String
	 *            String holding all topics 
	 */
	public String getTopics();
	
	/**
	 * Add a message to the message list corresponding to the given topic.
	 * 
	 * @param topic
	 *            The string defining the topic
	 * @param msg
	 *            The string defining the message
	 * @param name
	 *            The string defining the name of the user
	 */
	public boolean addMessage(String topic, String msg);
	
	/**
	 * Get last ten messages to the given topic.
	 * 
	 * @param topic
	 *            The string defining the topic
	 * @returns String
	 *            Last ten messages on that topic
	 */
	public String getMessages(String topic);
	
}