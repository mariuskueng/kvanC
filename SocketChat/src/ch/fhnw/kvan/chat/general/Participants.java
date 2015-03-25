package ch.fhnw.kvan.chat.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import ch.fhnw.kvan.chat.interfaces.IParticipants;

/**
 * The Participants stores and manages all participants. It adds/removes
 * participant names as they are joining or leaving the chat room in an
 * ArrayList. It stores all active participant names in form of a String as
 * well, which can be sent to a client to refresh its local participant list.
 * 
 * @see Participants
 * @author © ibneco, Rheinfelden
 * @version
 */

public class Participants implements IParticipants {
	private final List<String> participantList = Collections
			.synchronizedList(new ArrayList<String>());
	private String participantString = "";

	private final Logger logger2;

	public Participants() {
		logger2 = Logger.getLogger(Participants.class);
	}

	@Override
	public synchronized boolean addParticipant(String name) {
		logger2.info("creating new client:" + name);

		// add participant to list if not yet present
		if (!participantList.contains(name)) {
			participantList.add(name);
			Collections.sort(participantList);

			// create a new participantString from participants array
			StringBuffer participants = new StringBuffer();
			participants.append("participants=");
			String[] participantArray = new String[participantList.size()];
			participantList.toArray(participantArray);
			for (int i = 0; i < participantArray.length; i++) {
				participants.append(participantArray[i]);
				participants.append(";");
			}
			participantString = participants.toString();
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean removeParticipant(String name) {
		logger2.info("removing client:" + name);

		if (participantList.contains(name)) {
			participantList.remove(name);

			// create a new participantString from participants array
			StringBuffer participants = new StringBuffer();
			participants.append("participants=");
			String[] participantArray = new String[participantList.size()];
			participantList.toArray(participantArray);
			for (int i = 0; i < participantArray.length; i++) {
				participants.append(participantArray[i]);
				participants.append(";");
			}
			participantString = participants.toString();
			return true;
		}
		return false;
	}

	@Override
	public synchronized String getParticipants() {
		return participantString;
	}

}
