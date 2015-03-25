package ch.fhnw.kvan.chat.general;

import java.io.IOException;

import ch.fhnw.kvan.chat.interfaces.IChatRoom;

/**
 * The ChatRoom forwards all requests from clients to either the Participants or
 * the Chats class, which store/manage the participant names or respectively the
 * current topics and messages concerning those topics.
 * 
 * @see ChatRoom
 * @author © ibneco, Rheinfelden
 * @version
 */
public class ChatRoom implements IChatRoom {
	private final Participants participantInfo = new Participants();
	private final Chats chatInfo = new Chats();

	// To ensure singleton behaviour
	private static ChatRoom chatRoomInstance = null;

	private ChatRoom() {

	}

	public static ChatRoom getInstance() {
		if (chatRoomInstance == null) {
			chatRoomInstance = new ChatRoom();
		}
		return chatRoomInstance;
	}

	@Override
	public boolean addParticipant(String name) throws IOException {
		if (!name.trim().equalsIgnoreCase("")) {
			return participantInfo.addParticipant(name);
		} else {
			return false;
		}
	}

	@Override
	public boolean removeParticipant(String name) throws IOException {
		if (!name.trim().equalsIgnoreCase("")) {
			return participantInfo.removeParticipant(name);
		} else {
			return false;
		}
	}

	public String getParticipants() throws IOException {
		if (!participantInfo.getParticipants().equalsIgnoreCase("")) {
			return participantInfo.getParticipants();
		} else {
			return ("participants=");
		}
	}

	@Override
	public boolean addTopic(String topic) throws IOException {
		if (!topic.trim().equalsIgnoreCase("")) {
			return chatInfo.addTopic(topic);
		} else {
			return false;
		}
	}

	@Override
	public boolean removeTopic(String topic) throws IOException {
		if (!topic.trim().equalsIgnoreCase("")) {
			return chatInfo.removeTopic(topic);
		} else {
			return false;
		}
	}

	public String getTopics() throws IOException {
		if (!chatInfo.getTopics().equalsIgnoreCase("")) {
			return chatInfo.getTopics();
		} else {
			return ("topics=");
		}
	}

	@Override
	public boolean addMessage(String topic, String message) throws IOException {
		if (!topic.trim().equalsIgnoreCase("")
				&& !message.trim().equalsIgnoreCase("")) {
			return chatInfo.addMessage(topic, message);
		} else {
			return false;
		}
	}

	@Override
	public String getMessages(String topic) throws IOException {
		if (!topic.trim().equalsIgnoreCase("")) {
			return chatInfo.getMessages(topic);
		} else {
			return ("messages=");
		}
	}

	@Override
	public String refresh(String topic) throws IOException {
		if (!topic.trim().equalsIgnoreCase("")) {
			return chatInfo.getMessages(topic);
		} else {
			return ("messages=");
		}
	}

}
