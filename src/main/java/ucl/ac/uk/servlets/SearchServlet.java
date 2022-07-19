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

@WebServlet("/searchResult.html")
public class SearchServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = null;
        try {
            model = ModelFactory.getModel();
        } catch (Exception e) {
            e.printStackTrace();
        }


        String search = request.getParameter("searchstring").toLowerCase();
        //Sends searchstring to be compared with the titles of the notes
        ArrayList<String> matches = model.search(search);

        request.setAttribute("titles", matches);


        ServletContext context = getServletContext();
        RequestDispatcher dispatch;
        //If there are not matched notes show a different page showing that no notes were found.
        if (matches.size() > 0) {
            dispatch = context.getRequestDispatcher("/searchResults.jsp");
        } else {
            dispatch = context.getRequestDispatcher("/notFound.jsp");
        }
        dispatch.forward(request, response);
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
