package etu1763.framework.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

public class Front_Servlet extends HttpServlet {

public void ProcessRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        
        String url = req.getServletPath();
        
        out.println("URL : "+url);

    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ProcessRequest(req, res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ProcessRequest(req, res);
    }
    
}
