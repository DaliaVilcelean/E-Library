package servlet;


import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="UserLogin", value = "/UserLogin")
public class UserLogIn extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.println("<head>");
        out.println("<title>User Section</title>");
        out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
        out.println("</head>");
        out.println("<body>");

        String email=request.getParameter("email");
        String password=request.getParameter("password");
        if(UserDAO.authenticate(email,password)){

            HttpSession session=request.getSession();
            session.setAttribute("email",email);

            request.getRequestDispatcher("userPag.html").include(request, response);
            request.getRequestDispatcher("userCarousel.html").include(request, response);

        }else {

            request.getRequestDispatcher("homePag.html").include(request, response);
            out.println("<div class='container'>");
            out.println("<h3>Username or password error</h3>");
            request.getRequestDispatcher("userLogIn.html").include(request, response);
            out.println("</div>");

        }
        request.getRequestDispatcher("footer.html").include(request, response);
        out.close();


    }

    }
