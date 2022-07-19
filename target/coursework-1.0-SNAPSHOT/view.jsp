
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>View Note</title>
</head>
<body>
<div class = "main">
    <h2>
        <% // Retrieves the title, depending on where this page was accessed from, retrieves using two methods
            String title = request.getParameter("title");
        if(title == null){
            title = (String) request.getAttribute("title");
        }
            ArrayList<String> noteTitles = (ArrayList<String>) request.getAttribute("titles");
            int noteIndex = noteTitles.indexOf(title);
        %>
        <%=title%>
    </h2>
    <ul>
        <%ArrayList<String> entry = (ArrayList<String>) request.getAttribute("note");%>
        <li> Date created: <%=entry.get(1)%> </li>
        <li> Date modified: <%= entry.get(2)%> </li><br>
    </ul><br>

    <% //Gets and process the contents of the note. Converts to a url in html if that is its type.
        String ref = entry.get(3).replace("\n", "<br>");
        if(entry.get(4).equals("URL")){
            ref = "<a href = \"" + entry.get(3) + "\">" + entry.get(3) + "</a>";
        }
    %>

    <b> <%= ref%><b><br><br><br>
    <%String editref = "add.html?ind=" + noteIndex;%>
    <a href = "<%=editref%>">Click here to edit the note</a><br>
    <%String href = "delete.html?ind=" + noteIndex;%>
    <a href = "<%=href%>">Click here to delete note</a>

</div>
</body>
</html>
