<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Delete success</title>
</head>
<body>
<% //Retrieves the index of the passed note to be deleted and calls model to delete it
Model model = (Model) request.getAttribute("model");
String indexStr = request.getParameter("ind");
int index = Integer.parseInt(indexStr);
model.deleteNote(index);
%>
<p>Note successfully deleted</p><br>
<a href  = "index.html">Click here to return to main menu</a>
</body>
</html>