/*
 * Copyright (c) 2000-2009 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.kvan.chat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import ch.fhnw.kvan.chat.interfaces.IChatRoom;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame {

	// private Participant participant;
	private IChatRoom chatRoom;
	private String user;

	private final JComboBox<String> topiccombo = new JComboBox<String>();
	private final JComboBox<String> participantcombo = new JComboBox<String>();
	private final JButton btn_add_topic = new JButton("Add Topic");
	private final JButton btn_remove_topic = new JButton("Remove Topic");
	private final JButton btn_send_msg = new JButton("Send");
	private final JButton btn_get_msgs = new JButton("Get Msgs");
	private final JButton btn_refresh = new JButton("Refresh");

	private JTextArea enteredText = new JTextArea(10, 32);
	private JTextField topicText = new JTextField(32);
	private JTextField typedText = new JTextField(32);
	private final JMenuItem item_exit = new JMenuItem("Exit");
	private final JMenuItem item_about = new JMenuItem("About");

	private boolean ignoreItemChanges = false;

	private static Logger logger;

	public ClientGUI(IChatRoom client, String name) {
		// Log4J initialisation
		logger = Logger.getLogger(ClientGUI.class);

		this.chatRoom = client;
		this.user = name;
		setBackground(Color.lightGray);
		setResizable(false);
		// add all components and corresponding listeners to the ClientGUI frame
		addComponentsToFrame();
		setTitle("Chat Client 1.0: [" + user + "]");
		pack();
		setVisible(true);
	}

	public void addComponentsToFrame() {
		// define menus
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		JMenu menu_file = new JMenu("File");
		menubar.add(menu_file);
		menu_file.add(item_exit);

		JMenu menu_help = new JMenu("Help");
		menubar.add(menu_help);
		menu_help.add(item_about);

		item_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		item_about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				about();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});

		topiccombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (ignoreItemChanges)
					return;
				if (e.getStateChange() == ItemEvent.SELECTED) {
					updateTopicInfo((String) topiccombo.getSelectedItem());
					// get last messages to the selected topic
					if (getCurrentTopic() != null) {
						String currentTopic = getCurrentTopic().trim();
						try {
							chatRoom.getMessages(currentTopic);
						} catch (IOException ex) {
							logger.error("ClientGUI IOException: could not get messages to selected topic.");
						}
					} else {
						enteredText.setText("");
						enteredText.requestFocusInWindow();
					}
				}
			}
		});

		participantcombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (ignoreItemChanges)
					return;
				if (e.getStateChange() == ItemEvent.SELECTED)
					updateParticipantInfo((String) participantcombo
							.getSelectedItem());
			}
		});

		JPanel topic = new JPanel(new GridBagLayout());
		topic.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints c = new GridBagConstraints();

		// topic text
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 1;
		c.gridy = 0;
		topic.add(new JLabel("Topics: ", SwingConstants.RIGHT), c);

		// topic combo
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		topic.add(topiccombo, c);

		// participant text
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 1;
		topic.add(new JLabel("Participants: ", SwingConstants.RIGHT), c);

		// participant combo
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 1;
		topic.add(participantcombo, c);

		// messages text box
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.ipady = 40;
		c.weightx = 0.0;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 2;
		enteredText.setEditable(false);
		enteredText.setBackground(Color.LIGHT_GRAY);
		topic.add(new JScrollPane(enteredText), c);

		// remove topic button
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0; // reset to default !!!
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridwidth = 1; // 1 column wide
		c.gridy = 3; // 4th row
		topic.add(btn_remove_topic, c);

		// topic text
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridwidth = 2; // 2 columns wide
		c.gridy = 3; // 4th row
		topic.add(topicText, c);

		// add topic button
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 3;
		c.gridwidth = 1; // 1 column wide
		c.gridy = 3; // 4th row
		topic.add(btn_add_topic, c);

		// get 10 last messages
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridwidth = 1; // 1 column wide
		c.gridy = 4; // 5th row
		topic.add(btn_get_msgs, c);

		// typed text
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridwidth = 2; // 2 columns wide
		c.gridy = 4; // 5th row
		typedText.requestFocusInWindow();
		topic.add(typedText, c);

		// send button
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 3;
		c.gridwidth = 1; // 1 column wide
		c.gridy = 4; // 5th row
		topic.add(btn_send_msg, c);

		// refresh button
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.gridx = 0;
		c.gridwidth = 4; // 1 column wide
		c.gridy = 5; // 6th row
		topic.add(btn_refresh, c);

		add(topic);

		btn_send_msg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getCurrentTopic() != null) {
					String currentTopic = getCurrentTopic().trim();
					String currentMessage = typedText.getText().trim();
					if (!currentMessage.equalsIgnoreCase("")) {
						try {
							chatRoom.addMessage(currentTopic, currentMessage);
						} catch (IOException ex) {
							logger.error("ClientGUI IOException: could not send message.");
						}
					}
				}
				typedText.setText("");
				typedText.requestFocusInWindow();
			}
		});

		// react to ENTER
		typedText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent evt) {

			}

			@Override
			public void keyReleased(KeyEvent evt) {
				int c = evt.getKeyCode();
				if (c == KeyEvent.VK_ENTER) {
					// when typedText is reset to "", nothing should happen
					if (!typedText.getText().trim().equalsIgnoreCase("")) {
						if (getCurrentTopic() != null) {
							String currentTopic = getCurrentTopic().trim();
							String currentMessage = typedText.getText().trim();
							try {
								chatRoom.addMessage(currentTopic,
										currentMessage);
							} catch (IOException ex) {
								logger.error("ClientGUI IOException: could not send message.");
							}
						}
					}
					typedText.setText("");
					typedText.requestFocusInWindow();
				}
			}

			@Override
			public void keyTyped(KeyEvent evt) {

			}
		});

		btn_add_topic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String topic = topicText.getText().trim();
				if (!topic.equalsIgnoreCase("")) {
					try {
						chatRoom.addTopic(topic);
					} catch (IOException ex) {
						logger.error("ClientGUI IOException: could not add topic "
								+ topic);
					}
					topicText.setText("");
					topicText.requestFocusInWindow();
				}

			}
		});

		btn_remove_topic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String topic = topicText.getText().trim();
				if (!topic.equalsIgnoreCase("")) {
					try {
						chatRoom.removeTopic(topic);
					} catch (IOException ex) {
						logger.error("ClientGUI IOException: could not remove topic "
								+ topic);
					}
					topicText.setText("");
					topicText.requestFocusInWindow();
				}
			}
		});

		btn_get_msgs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getCurrentTopic() != null) {
					String currentTopic = getCurrentTopic().trim();
					try {
						chatRoom.getMessages(currentTopic);
					} catch (IOException ex) {
						logger.error("ClientGUI IOException: could not get messages.");
					}
				}
			}
		});

		btn_refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getCurrentTopic() != null) {
					String currentTopic = getCurrentTopic().trim();
					try {
						chatRoom.refresh(currentTopic);
					} catch (IOException ex) {
						logger.error("ClientGUI IOException: could not refresh.");
					}
				}
			}
		});
	}

	// action triggered by server
	public void removeParticipant(String name) {
		ignoreItemChanges = true;
		participantcombo.removeItem(name);
		ignoreItemChanges = false;
		changeNotification(name, "remove_participant");
	}

	// action triggered by server
	public void addParticipant(String name) {
		ignoreItemChanges = true;
		participantcombo.addItem(name);
		participantcombo.setSelectedItem(name);
		ignoreItemChanges = false;
		changeNotification(name, "add_participant");
	}

	private void updateTopicInfo(String currentTopic) {
		logger.info("ClientGUI: changed topic=" + currentTopic);
	}

	private void updateParticipantInfo(String currentParticipant) {
		logger.info("ClientGUI: changed participant=" + currentParticipant);
	}

	public String getCurrentTopic() {
		return (String) topiccombo.getSelectedItem();
	}

	// action triggered by server
	public void updateTopics(String[] topics) {
		ignoreItemChanges = true;
		String selectedTopic = (String) topiccombo.getSelectedItem();
		topiccombo.removeAllItems();
		for (int i = 0; i < topics.length; i++) {
			topiccombo.addItem(topics[i]);
			topiccombo.setSelectedItem(topics[i]);
		}
		if (selectedTopic != null) {
			topiccombo.setSelectedItem(selectedTopic);
		}
		ignoreItemChanges = false;
	}

	// action triggered by server
	public void addTopic(String topic) {
		ignoreItemChanges = true;
		topiccombo.addItem(topic);
		ignoreItemChanges = false;
		changeNotification(topic, "add_topic");
	}

	// action triggered by server
	public void removeTopic(String topic) {
		ignoreItemChanges = true;
		topiccombo.removeItem(topic);
		ignoreItemChanges = false;
		changeNotification(topic, "remove_topic");

		// after removal, get last messages concerning the automatically
		// selected topic
		if (getCurrentTopic() != null) {
			String currentTopic = getCurrentTopic().trim();
			try {
				chatRoom.getMessages(currentTopic);
			} catch (IOException ex) {
				logger.error("ClientGUI IOException: could not get messages to selected topic.");
			}
		} else {
			enteredText.setText("");
			enteredText.requestFocusInWindow();
		}
	}

	// action triggered by server
	public void updateParticipants(String[] participants) {
		ignoreItemChanges = true;
		participantcombo.removeAllItems();
		for (int i = 0; i < participants.length; i++) {
			participantcombo.addItem(participants[i]);
			participantcombo.setSelectedItem(participants[i]);
		}
		ignoreItemChanges = false;
	}

	public void addMessage(String message) {
		enteredText.insert(message + "\n", enteredText.getText().length());
		enteredText.setCaretPosition(enteredText.getText().length());
	}

	public void updateMessages(String[] messages) {
		enteredText.setText("");

		if (messages.length != 0) {
			for (int i = 0; i < messages.length; i++) {
				enteredText.insert(messages[i] + "\n", enteredText.getText()
						.length());
				enteredText.setCaretPosition(enteredText.getText().length());
			}
		}

	}

	public void exit() {
		try {
			chatRoom.removeParticipant(user);
		} catch (IOException e) {
			logger.error("ClientGUI exit I/O:" + e.getMessage());
		}
		System.exit(0);
	}

	public void error(Exception e) {
		JDialog dlg = new ErrorBox(this, e);
		dlg.setModal(true);
		dlg.setVisible(true);
	}

	public void about() {
		AboutBox dlg = new AboutBox(this);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
				(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		dlg.setVisible(true);
	}

	public void changeNotification(String topic, String action) {
		AlertBox dlg = new AlertBox(this, topic, action);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
				(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		dlg.setVisible(true);
	}

	static class ErrorBox extends JDialog {
		public ErrorBox(Frame parent, Exception e) {
			super(parent);
			setTitle("Exception");
			setResizable(true);

			JTextArea trace = new JTextArea(10, 50);
			java.io.StringWriter buf = new java.io.StringWriter();
			java.io.PrintWriter wr = new java.io.PrintWriter(buf);
			e.printStackTrace(wr);
			trace.setText(buf.toString());
			trace.setCaretPosition(0);
			trace.setEditable(false);

			JScrollPane msg = new JScrollPane(trace);

			JButton ok = new JButton("OK");

			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			getContentPane().add(msg, BorderLayout.CENTER);
			getContentPane().add(ok, BorderLayout.SOUTH);
			getRootPane().setDefaultButton(ok);
			ok.requestFocus();
			pack();
		}
	}

	static class AboutBox extends JDialog {

		public AboutBox(Frame parent) {
			super(parent);
			setTitle("About Chat Client");
			setResizable(false);

			JPanel p_text = new JPanel(new GridLayout(0, 1));
			p_text.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
			p_text.add(new JLabel("Distributed Systems", SwingConstants.CENTER));
			p_text.add(new JLabel("Chat Client", SwingConstants.CENTER));
			p_text.add(new JLabel("", SwingConstants.CENTER));
			p_text.add(new JLabel("© ibneco, Rheinfelden, 2012",
					SwingConstants.CENTER));

			JButton ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			getContentPane().add(p_text, BorderLayout.CENTER);
			getContentPane().add(ok, BorderLayout.SOUTH);
			pack();
		}
	}

	static class AlertBox extends JDialog {

		public AlertBox(Frame parent, String what, String action) {
			super(parent);
			setTitle("Chat Client");
			setResizable(false);

			JPanel p_text = new JPanel(new GridLayout(0, 1));
			p_text.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
			if (action.equals("add_topic")) {
				p_text.add(new JLabel("New topic added: " + what + " !",
						SwingConstants.CENTER));
			} else if (action.equals("remove_topic")) {
				p_text.add(new JLabel("Topic: " + what + " has been removed!",
						SwingConstants.CENTER));
			}
			if (action.equals("add_participant")) {
				p_text.add(new JLabel("New participant joined: " + what + " !",
						SwingConstants.CENTER));
			} else if (action.equals("remove_participant")) {
				p_text.add(new JLabel("Participant: " + what + " left!",
						SwingConstants.CENTER));
			}

			JButton ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			getContentPane().add(p_text, BorderLayout.CENTER);
			getContentPane().add(ok, BorderLayout.SOUTH);
			pack();
		}
	}
}
