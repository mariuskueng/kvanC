package ch.fhnw.kvan.chat.servlet;
import ch.fhnw.kvan.chat.general.ChatRoom;
import ch.fhnw.kvan.chat.general.ChatRoomDriver;
import javax.servlet.* ;
import javax.servlet.http.* ;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;


public class Server extends HttpServlet {
    private static Logger logger = Logger.getLogger(Server.class);
    private static ChatRoomDriver chatRoomDriver;
    private static ChatRoom chatRoom;

    public void init() throws ServletException {
        super.init();
        chatRoomDriver = new ChatRoomDriver();
        chatRoomDriver.connect("localhost", 8080);
        chatRoom = (ChatRoom) chatRoomDriver.getChatRoom();
        logger.info("Servlet initialised");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = "";
        String name = "";
        String topic = "";
        String message = "";

        logger.info("Request: " + request);

        action = request.getParameter("action");

        if(action != null) {

            response.setContentType("text/html");

            switch (action) {
                case "addParticipant":
                    name = null;
                    name = request.getParameter("name");
                    if(name != null) {
                        chatRoom.addParticipant(name);
                        response.setStatus(200);
                        logger.info("added participant: " + name);
                    } else {
                        response.sendError(400, "username is needed");
                        logger.info("FAILED: adding participant");
                    }
                    break;

                case "removeParticipant":
                    name = null;
                    name = request.getParameter("name");
                    if(name != null) {
                        chatRoom.removeParticipant(name);
                        response.setStatus(200);
                        logger.info("removed participant: " + name);
                    } else {
                        response.sendError(400, "username is needed");
                        logger.info("FAILED: removing participant");
                    }
                    break;

                case "addTopic":
                    topic = null;
                    topic = request.getParameter("topic");
                    if(topic != null) {
                        chatRoom.addTopic(topic);
                        response.setStatus(200);
                        logger.info("added topic: " + topic);
                    } else {
                        response.sendError(400, "name of the topic is needed");
                        logger.info("FAILED: adding topic");
                    }
                    break;

                case "removeTopic":
                    topic = null;
                    topic = request.getParameter("topic");
                    if(topic != null) {
                        chatRoom.addTopic(topic);
                        response.setStatus(200);
                        logger.info("added topic: " + topic);
                    } else {
                        response.sendError(400, "name of the topic is needed");
                        logger.info("FAILED: adding topic");
                    }
                    break;

                case "postMessage":
                    topic = null;
                    topic = request.getParameter("topic");
                    message = null;
                    message = request.getParameter("message");
                    if(topic != null && message != null) {
                        chatRoom.addMessage(topic, message);
                        response.setStatus(200);
                        logger.info("posted to topic: " + topic + " message: " + message);
                    } else {
                        response.sendError(400, "parameter(s) missing");
                        logger.info("FAILED: posting Message");
                    }
                    break;

                case "getMessages":
                    topic = null;
                    topic = request.getParameter("topic");
                    if(topic != null) {
                        String messages = chatRoom.getMessages(topic);

                        PrintWriter out = response.getWriter();
                        out.println(messages);
                        out.close();

                        logger.info("got messages for topic: " + topic);
                    } else {
                        response.sendError(400, "name of the topic is needed");
                        logger.info("FAILED: get messages");
                    }
                    break;

                case "refresh":
                    topic = null;
                    topic = request.getParameter("topic");
                    if(topic != null) {
                        String messages = chatRoom.getMessages(topic);
                        String topics = chatRoom.getTopics();
                        String participants = chatRoom.getParticipants();

                        PrintWriter out = response.getWriter();
                        out.println(messages);
                        out.println(topics);
                        out.println(participants);
                        out.close();

                        response.setStatus(200);
                        logger.info("refresh for topic: " + topic);
                    } else {
                        response.sendError(400, "name of the topic is needed");
                        logger.info("FAILED: refresh");
                    }
                    break;
                default:
                    PrintWriter out = response.getWriter();
                    out.println("<h1>kvanC servlet Chatroom</h1>");
                    out.println("<p>Please send a valid request from the GUI client!</p>");
                    out.close();
            }

        } else {
            response.sendError(400, "action is needed");
            logger.info("FAILED: no action given");
        }



    }
}
