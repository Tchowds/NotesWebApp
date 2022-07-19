package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add.html")
public class addServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean received = false;
        Model model = null;

        try {model = ModelFactory.getModel();}
        catch (Exception e) {e.printStackTrace();}


        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        String indexStr = request.getParameter("ind");


        //Only occurs when submit is pressed on the add page and both title and contents box had information
        if(title != null && contents != null && title.length() > 0 && contents.length() > 0){
            received = true;
            //the link ind parameter will determine if the user is adding a new note or editing an existing note
            if(indexStr != null){
                int index = Integer.parseInt(indexStr);
                model.updateNote(index, title, contents);
            }
            else{model.addNote(title, contents);
            }
        }

        request.setAttribute("model", model);
        request.setAttribute("confirm", received);
        //Attributes available:
        //model - the model
        //received - Lets the view know if a note has been added/edited


        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/add.jsp");
        dispatch.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
