package ch.fhnw.kvan.chat.general;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;

import ch.fhnw.kvan.chat.interfaces.IChats;

/**
 * The Chats stores and manages all topics and incoming messages regarding those
 * topics. It adds/removes topics, saves messages, retrieves the last ten
 * messages on a given topic, and in case that a complete refresh is requested:
 * it retrieves the last ten messages and the topic list as well.
 * 
 * @see Chats
 * @author © ibneco, Rheinfelden
 * @version
 */
public class Chats implements IChats {
	private final List<String> topicList = Collections
			.synchronizedList(new ArrayList<String>());
	private String topicString = "";
	private final Map<String, List<String>> topicMessagesMap = Collections
			.synchronizedMap(new HashMap<String, List<String>>());

	private final Logger logger3;

	public Chats() {
		logger3 = Logger.getLogger(Chats.class);
	}

	@Override
	public synchronized boolean addTopic(String topic) {
		// add topic to topicList if not yet present
		if (!topicList.contains(topic)) {
			topicList.add(topic);
			Collections.sort(topicList);

			// add new topic to the topicMessagesMap
			topicMessagesMap.put(topic,
					Collections.synchronizedList(new ArrayList<String>()));

			logger3.info("adding new topic:" + topic);

			// create a new topicString from topics array
			StringBuffer topics = new StringBuffer();
			topics.append("topics=");
			String[] topicArray = new String[topicList.size()];
			topicList.toArray(topicArray);
			for (int i = 0; i < topicArray.length; i++) {
				topics.append(topicArray[i]);
				topics.append(";");
			}
			topicString = topics.toString();
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean removeTopic(String topic) {
		if (topicList.contains(topic)) {
			topicList.remove(topic);
			topicMessagesMap.remove(topic);
			logger3.info("removing topic:" + topic);

			// create a new topicString from topics array
			StringBuffer topics = new StringBuffer();
			topics.append("topics=");
			String[] topicArray = new String[topicList.size()];
			topicList.toArray(topicArray);
			for (int i = 0; i < topicArray.length; i++) {
				topics.append(topicArray[i]);
				topics.append(";");
			}
			topicString = topics.toString();

			return true;
		}
		return false;
	}

	@Override
	public synchronized String getTopics() {
		return topicString;
	}

	@Override
	public synchronized boolean addMessage(String topic, String msg) {
		if (topicList.contains(topic)) {
			if (topicMessagesMap.containsKey(topic)) {
				List<String> messages = topicMessagesMap.get(topic);
				messages.add(msg);
				return true;
			}
		}
		return false;
	}

	// triggered by client
	@Override
	public synchronized String getMessages(String topic) {
		if (topicMessagesMap.containsKey(topic)) {
			List<String> messages = topicMessagesMap.get(topic);
			ListIterator<String> it = messages.listIterator(messages.size());
			int i = 0;

			StringBuffer msgs = new StringBuffer();
			msgs.append("messages=");
			while (it.hasPrevious() && i <= 10) {
				i++;
				msgs.append(it.previous());
				msgs.append(";;");
			}
			return msgs.toString();
		}
		return ("messages=");
	}
}
