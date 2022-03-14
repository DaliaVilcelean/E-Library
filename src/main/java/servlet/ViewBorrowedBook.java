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
import java.util.List;

@WebServlet(name="ViewBorrowedBook",value="/ViewBorrowedBook")
public class ViewBorrowedBook extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.println("<head>");
        out.println("<title>View Borrowed Book</title>");
        out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
        out.println("</head>");
        out.println("<body>");
        request.getRequestDispatcher("userPag.html")
                .include(request, response);

        out.println("<div class='container'>");

        List<BorrowedBook> list=BookDAO.viewBorrowedBooks();

        out.println("<table class='table table-bordered table-striped'>");
        out.println("<tr><th>Call</th><th>Student Id</th><th>Student Name</th>" +
                "<th>Student Mobile</th><th>Issued Date</th><th>Return Status</th></tr>");
        for(BorrowedBook bean:list){
            out.println("<tr><td>"+bean.getCallno()+"</td><td>"+bean.getStudentid()
                    +"</td><td>"+bean.getStudentname()+"</td><td>"
                    +bean.getStudentmobile()+"</td><td>"
                    +bean.getIssueddate()+"</td><td>"+bean.getReturnstatus()+
                    "</td></tr>");
        }
        out.println("</table>");

        out.println("</div>");



        request.getRequestDispatcher("footer.html").include(request, response);
        out.close();
    }


}
