package etu1763.framework.servlet;

import etu1763.framework.Mapping;
import utilPerso.Utilitaire;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Front_Servlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    
    @Override
    public void init() throws ServletException {
        try {
            String repertoire = getServletContext().getRealPath("/WEB-INF/classes/");
            mappingUrls = Utilitaire.whoClass(repertoire, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public void ProcessRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        
        String url = req.getServletPath();
        
        out.println("URL : "+url);
        
        out.println("\n\nMappingUrls :");

        for (Map.Entry me : this.mappingUrls.entrySet()) {
            out.println("Key : "+me.getKey()+", Class : "+((Mapping)me.getValue()).getClassName()+", methode : "+((Mapping)me.getValue()).getMethode());
        }

        out.println("\n\nL'URL est supportée : "+this.mappingUrls.containsKey(url));

    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ProcessRequest(req, res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ProcessRequest(req, res);
    }
    
}
