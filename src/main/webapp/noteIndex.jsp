<%@ page import="java.util.ArrayList" %>
<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Note Index</title>
</head>
<body>
<div class = "main">
    <h2>Notes:</h2>


    <%String type = request.getParameter("type");
    String suffix = "";
    ArrayList<String> summaries = new ArrayList<>();
    if(type != null){suffix = "type=" + type;}
    String summary = request.getParameter("summary");
    if(summary == null){suffix = suffix + "&summary=show";}
    else{summaries = (ArrayList<String>) request.getAttribute("summaries");}



    %>


    <a href = "noteIndex.html?<%=suffix%>">Click here to toggle summary of notes</a><br>
    <a href = "noteIndex.html?type=sorted"> Click here to view notes in alphabetical order </a><br>
    <a href = "noteIndex.html?type=date"> Click here to view notes in data created order (newest to oldest)</a><br>
    <a href = "noteIndex.html?type=mod">Click here to view notes in date modified order (newest to oldest)</a><br>
    <ul>
        <%
            Model model = (Model) request.getAttribute("model");
            ArrayList<String> noteTitles = (ArrayList<String>) request.getAttribute("titles");
            ArrayList<String> defaultTitles = (ArrayList<String>) request.getAttribute("oldTitles");
            String noteSummary = "";

            for(String note: noteTitles){
                //model.getNoteSummary(noteTitles.indexOf(note));
                if(summary != null){ noteSummary = model.getNoteSummary(defaultTitles.indexOf(note));}
                String href = "view.html?title=" + note;

        %>
        <li><a href = "<%=href%>"><%=note%></a>
        </li>
        <%=noteSummary%>
        <% } %>
    </ul>
    <a href = "index.html">Click here to go back to the main menu</a>
</div>
</body>
</html>
