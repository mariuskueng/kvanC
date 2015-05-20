package ch.fhnw.kvan.chat.servlet;

import ch.fhnw.kvan.chat.gui.ClientGUI;
import ch.fhnw.kvan.chat.interfaces.IChatRoom;
import ch.fhnw.kvan.chat.utils.In;
import ch.fhnw.kvan.chat.utils.Out;

import java.net.*;

import org.apache.http.HttpRequest;
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

	private Out outputStream;
	private In inputStream;

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

        HttpUriRequest req = new HttpGet("http://" + path); // + "?action=addParticipant&name=alice");
        try {
            httpClient.execute(req);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        // this.outputStream = new Out(url);
        // this.outputStream.print();

        logger.info("Client instance started");
    }

    @Override
    public boolean addParticipant(String name) throws IOException {
        return false;
    }

    @Override
    public boolean removeParticipant(String name) throws IOException {
        return false;
    }

    @Override
    public boolean addTopic(String topic) throws IOException {
        return false;
    }

    @Override
    public boolean removeTopic(String topic) throws IOException {
        return false;
    }

    @Override
    public boolean addMessage(String topic, String message) throws IOException {
        return false;
    }

    @Override
    public String getMessages(String topic) throws IOException {
        return null;
    }

    @Override
    public String refresh(String topic) throws IOException {
        return null;
    }
}
