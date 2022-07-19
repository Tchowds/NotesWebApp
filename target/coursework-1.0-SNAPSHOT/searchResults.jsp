<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results</title>
</head>
<body>
<h1>Search results:</h1><br>
<%
    //Very similar to the index jsp but without a lot of the processing to figure out the sorting
    ArrayList<String> noteTitles = (ArrayList<String>) request.getAttribute("titles");

    for(String note: noteTitles){
        String href = "view.html?title=" + note;

%>
<li><a href = "<%=href%>"><%=note%></a>
</li>
<% } %>

</body>
</html>
