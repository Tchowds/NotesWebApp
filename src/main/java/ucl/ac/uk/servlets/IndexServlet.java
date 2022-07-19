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



@WebServlet("/noteIndex.html")
public class IndexServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = null;

        try {
            model = ModelFactory.getModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("model", model);

        String type = request.getParameter("type");

        ArrayList<String> titles = model.getNoteNames(type);
        ArrayList<String> originalTitles = model.getNoteNames(null);

        request.setAttribute("titles", titles);
        request.setAttribute("oldTitles", originalTitles);

        //Available attributes for jsp
        //model - model
        //titles - titles of all notes but ordered depending on the type parameter of the page
        //oldTitles - the titles of all notes but not sorted

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/noteIndex.jsp");
        dispatch.forward(request, response);
    }
}
