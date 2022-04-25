package com.dataken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hostname = InetAddress.getLocalHost().getHostName();
        HttpSession session = req.getSession(false);
        if ( session == null ) {
            session = req.getSession();
        }
        boolean newSession = session.isNew();
        String id = req.getParameter("id");
        System.out.println(">>>>>>>>>>>>>>> ID Received: " + id);
        if ( id != null && id.equals("1") ) {
            session.setAttribute("currentTime", new Date());
        }
        resp.getWriter().print("<p>Hostname is: " + hostname + "</p><br>" +
                "<p>New Session: " + newSession + "</p><br>" +
                "<p>ID: " + id + "</p><br>" +
                "<p>Session time: " + session.getAttribute("currentTime") + "</p>");
        System.out.println(">>>>>>>>>>>>> Done with handling of the request ...");
    }
}
