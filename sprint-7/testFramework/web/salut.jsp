<%-- 
    Document   : salut
    Created on : 4 avr. 2023, 11:14:57
    Author     : andriniaina
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Emp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% ArrayList<Emp> liste = (ArrayList<Emp>)request.getAttribute("liste"); %>
        <form action="process.run" methode="POST">
            <h1>Formulaire</h1>
            <p>Nom</p>
            <input type="text" name="nom"placeholder="Votre nom">
            <br><br>
            <p>Place</p>
            <select name="id">
                <% for (Emp emp : liste) { %>
                    <option value="<%= emp.getId() %>"><%= emp.getNom() %></option>
                <% } %>
            </select>
            <input type="submit" value="Valider" />
        </form>
        
        
    </body>
</html>
