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
import java.util.ArrayList;

@WebServlet("/view.html")
public class ViewServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = null;
        try {
            model = ModelFactory.getModel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String title = request.getParameter("title");
        ArrayList<String> titles = model.getNoteNames(null);
        request.setAttribute("titles", titles);

        int index = titles.indexOf(title);
        ArrayList<String> entry = model.getNoteContents(index);
        request.setAttribute("note", entry);

        //Attributes available:
        //titles - unsorted titles
        //note - contents of the note whose index is the parameter from the link

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/view.jsp");
        dispatch.forward(request, response);
    }
}
