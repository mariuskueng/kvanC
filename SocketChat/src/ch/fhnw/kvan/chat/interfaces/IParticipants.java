/*
 * Copyright (c) 2012 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.kvan.chat.interfaces;
public interface IParticipants {
	
	/**
	 * Add a participant to the participant list and participant string.
	 * 
	 * @param name
	 *            The name of the participant

	 */
	public boolean addParticipant(String name);

	/**
	 * Remove a participant from the participant list and participant string.
	 * 
	 * @param name
	 *            The name of the participant
	 */
	public boolean removeParticipant(String name);
		
	/**
	 * Get participant string.
	 * 
	 * @returns String
	 *            String holding all participant names 
	 */
	public String getParticipants();
	
}