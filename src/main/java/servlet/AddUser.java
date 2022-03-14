package servlet;


import beans.User;
import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="AddUser",value="/AddUser")
public class AddUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.println("<head>");
        out.println("<title>Register</title>");
        out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
        out.println("</head>");
        out.println("<body>");

        request.getRequestDispatcher("index.html").include(request, response);
        out.println("<div class='container'>");

        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String smobile=request.getParameter("mobile");
        long mobile=Long.parseLong(smobile);
        User bean=new User(name, email, password, mobile);
        UserDAO.save(bean);
        out.print("<h4>User added successfully</h4>");
        request.getRequestDispatcher("index.html").include(request, response);


        out.println("</div>");
        request.getRequestDispatcher("footer.html").include(request, response);
        out.close();


    }

}
