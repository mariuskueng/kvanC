package ch.fhnw.kvan.chat.servlet;

import ch.fhnw.kvan.chat.gui.ClientGUI;
import ch.fhnw.kvan.chat.interfaces.IChatRoom;
import ch.fhnw.kvan.chat.utils.In;
import ch.fhnw.kvan.chat.utils.Out;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;

public class Client implements IChatRoom {
	private String name;
	private String path;
	private ClientGUI gui;

    private URI uri;
    private HttpClient httpClient;

    private static Logger logger = Logger.getLogger(Client.class);

	public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new Exception("Missing arguments either <url> or <name>");
        } else {
            new Client(args[0], args[1]);
        }
	}

    public Client (String n, String p) throws IOException, URISyntaxException {
        this.name = n;
        this.path = p;
        this.gui = new ClientGUI(this, this.name);

        this.uri = new URIBuilder().setScheme("http").setHost(this.path).build();
        this.httpClient = HttpClientBuilder.create().build();

        // Initialise the User
        addParticipant(name);
        refresh("");


        logger.info("Client instance started");
    }

    @Override
    public boolean addParticipant(String name) throws IOException {
        // the client signs in
        HttpUriRequest req= new HttpGet("http://" + path + "?action=addParticipant&name=" + name);
        try {
            httpClient.execute(req);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeParticipant(String name) throws IOException {
        // the client signs out
        HttpUriRequest req= new HttpGet("http://" + path + "?action=removeParticipant&name=" + name);
        try {
            httpClient.execute(req);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addTopic(String topic) throws IOException {
        // client adds a new topic
        HttpUriRequest req= new HttpGet("http://" + path + "?action=addTopic&topic=" + URLEncoder.encode(topic, "UTF-8"));
        try {
            httpClient.execute(req);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeTopic(String topic) throws IOException {
        // client removes an existing topic
        HttpUriRequest req= new HttpGet("http://" + path + "?action=removeTopic&topic=" + URLEncoder.encode(topic, "UTF-8"));
        try {
            httpClient.execute(req);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addMessage(String topic, String message) throws IOException {
        // client sends a message to a given topic (client name needs to be added before sending!)
        message = this.name + ": " + message;
        HttpUriRequest req= new HttpGet("http://" + path + "?action=postMessage&message='" + URLEncoder.encode(message, "UTF-8")
                + "'&topic=" + URLEncoder.encode(topic, "UTF-8"));
        try {
            httpClient.execute(req);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public String getMessages(String topic) throws IOException {
        // request the last 10 messages to a given topic
        HttpUriRequest req= new HttpGet("http://" + path + "?action=getMessages&topic=" + URLEncoder.encode(topic, "UTF-8"));
        try {
            HttpResponse httpResponse = httpClient.execute(req);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            parseMessages(reader.readLine());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String refresh(String topic) throws IOException {
        // request the last 10 messages to a given topic
        // AND the current lists of participants and topics
        HttpUriRequest req= new HttpGet("http://" + path + "?action=refresh&topic=" + URLEncoder.encode(topic, "UTF-8"));
        try {
            HttpResponse httpResponse = httpClient.execute(req);
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

            String[] lines =  new String[3];
            for(int i=0; i<3; i++){
                lines[i]=reader.readLine();
            }
            for(int i=0; i<3; i++){
                if(lines[i].startsWith("messages")) {
                    parseMessages(lines[i]);
                } else if (lines[i].startsWith("topics")) {
                    parseTopics(lines[i]);
                } else if (lines[i].startsWith("participants")) {
                    parseParticipants(lines[i]);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void parseTopics(String input) {
        String topics;
        try{
            topics = input.split("=")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            topics = "";
        }
        gui.updateTopics(topics.split(";"));
    }
    public void parseMessages(String input) {
        String messages;
        try{
            messages = input.split("=")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            messages = "";
        }
        gui.updateMessages(messages.split(";"));

    }
    public void parseParticipants(String input) {
        String participants;
        try{
            participants = input.split("=")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            participants = "";
        }
        gui.updateParticipants(participants.split(";"));
    }

}
