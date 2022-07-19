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

@WebServlet("/delete.html")
public class deleteServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Model model = null;

        try {
            model = ModelFactory.getModel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("model", model);
        //Attribute available: model - the model

        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/deleteSuccess.jsp");
        dispatch.forward(request, response);
    }
}
