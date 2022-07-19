<%@ page import="ucl.ac.uk.main.Model" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Note</title>
</head>
<body>
<h1>Fill in the details to add/edit your note</h1>

<% Model model = (Model) request.getAttribute("model");
String indexStr = request.getParameter("ind");
String href = "add.html";
String editTitle = "";
String editContents = "";


//If index parameter in the url is there (as in if page has been retrieved by selecting "edit page") retrive autofill data in the boxes of the note to edit.
if(indexStr != null){
    ArrayList<String> entry = model.getNoteContents(Integer.parseInt(indexStr));
    editTitle = entry.get(0);
    editContents = entry.get(3);
    String urlSuffix = "?ind=" + indexStr;
    href = href + urlSuffix;
    //Upon submission instead of returning to a blank form, the page shows the edits of t
}
%>


<form action = "<%=href%>" method = "POST">

    Title: <textarea name = "title" rows = "1" cols = "50"><%=editTitle%></textarea>
    <br />

    Contents: <textarea name = "contents" rows = "6" cols = "50"><%=editContents%></textarea>
    <input type = "submit" value = "Submit" />
</form>




<% boolean confirm = (boolean) request.getAttribute("confirm");
//if a note has been added/edited the message changes. addServlet specified this behaviour
String success = "Waiting";
if(confirm){
    success = "Note successfully added/ edited";
}
%>

<p><%= success%></p>


<a href = "index.html">Click here to return to main page</a>
</body>
</html>
