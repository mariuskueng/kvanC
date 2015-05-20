package ch.fhnw.kvan.chat.servlet;
import ch.fhnw.kvan.chat.general.ChatRoom;
import javax.servlet.* ;
import javax.servlet.http.* ;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

@WebServlet("/chat/Server/")
public class Server extends HttpServlet {
    private static ChatRoom chatRoom = ChatRoom.getInstance();
    private static Logger logger = Logger.getLogger(Server.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.write("<h1>Hello</h1>");
        out.close();
        logger.info(request);
    }
}
