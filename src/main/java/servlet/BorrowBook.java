package servlet;



import beans.BorrowedBook;
import dao.BookDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="BorrowBook",value="/BorrowBook")
public class BorrowBook extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.println("<head>");
        out.println("<title>Add Book Form</title>");
        out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
        out.println("</head>");
        out.println("<body>");
        request.getRequestDispatcher("userPag.html").include(request, response);

        out.println("<div class='container'>");
        String callno=request.getParameter("callno");
        String studentid=request.getParameter("studentid");
        String studentname=request.getParameter("studentname");
        String sstudentmobile=request.getParameter("studentmobile");
        long studentmobile=Long.parseLong(sstudentmobile);

        BorrowedBook bean=new BorrowedBook(callno,studentid,studentname,studentmobile);
        int i=BookDAO.borrowBook(bean);
        if(i>0){
            out.println("<h3>Book borrowed successfully</h3>");
        }else{
            out.println("<h3>Sorry, unable to issue book.</h3><p>We may have sortage of books. Kindly visit later.</p>");
        }
        out.println("</div>");


        request.getRequestDispatcher("footer.html").include(request, response);
        out.close();


    }


    }
