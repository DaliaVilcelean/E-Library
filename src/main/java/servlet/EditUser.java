package servlet;

import beans.User;
import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="EditUser",value="/EditUser")
public class EditUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid=request.getParameter("id");
        int id=Integer.parseInt(sid);
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String smobile=request.getParameter("mobile");
        long mobile=Long.parseLong(smobile);
        User bean=new User(id,name, email, password, mobile);
        UserDAO.update(bean);

        response.sendRedirect("ViewUser");
    }


}
