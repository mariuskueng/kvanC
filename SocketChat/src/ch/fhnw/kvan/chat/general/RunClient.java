/*
 * Copyright (c) 2000-2009 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.kvan.chat.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class RunClient extends JFrame {
	private static final long serialVersionUID = 1107851134623110939L;
	private static Logger logger;

	private static JFrame frame;

	public static void main(String args[]) {
		// Set up a simple configuration that logs on the console
		BasicConfigurator.configure();

		frame = new RunClient();
		frame.pack();
		frame.setVisible(true);
	}

	RunClient() {
		super("Choose Chat Client");

		// Log4J initialization
		logger = Logger.getLogger(RunClient.class);

		Vector<String> vect = new Vector<String>();

		try {
			java.net.URL url = getClass().getResource("Clients.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(url.getFile())));
			String line = in.readLine();
			while (line != null) {
				vect.add(line);
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			logger.error("RunClient could not the Choose Chat Client text file.");
		}

		final JList<String> list = new JList<String>(vect);
		getContentPane().add(list);

		JButton b = new JButton("start");
		getContentPane().add(b, "South");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startChat((String) list.getSelectedValue());
			}
		});

	}

	void startChat(String arg) {
		frame.setVisible(false);
		frame.dispose();

		StringTokenizer tok = new StringTokenizer(arg);
		String[] args = new String[tok.countTokens()];
		for (int i = 0; i < args.length; i++) {
			args[i] = tok.nextToken();
		}
		StartClient.main(args);
	}

}
