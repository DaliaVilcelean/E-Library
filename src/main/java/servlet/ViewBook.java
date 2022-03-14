package servlet;

import beans.Book;
import dao.BookDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="ViewBook",value="/ViewBook")
public class ViewBook extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.println("<head>");
        out.println("<title>View Book</title>");
        out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
        out.println("</head>");
        out.println("<body>");
        request.getRequestDispatcher("userPag.html").include(request, response);

        out.println("<div class='container'>");

        List<Book> list=BookDAO.view();

        out.println("<table class='table table-bordered table-striped'>");
        out.println("<tr><th>nr</th><th>Name</th><th>Author</th>" +
                "<th>Publisher</th><th>Quantity</th><th>Borrowed</th>" +
                "<th>Delete</th></tr>");
        for(Book book:list){
            out.println("<tr><td>"+book.getCallno()+"</td><td>"
                    +book.getName()+"</td><td>"+book.getAuthor()
                    +"</td><td>"+book.getPublisher()+"</td><td>"
                    +book.getQuantity()+"</td><td>"+book.getIssued()
                    +"</td><td><a href='DeleteBook?callno="
                    +book.getCallno()+"'>Delete</a></td></tr>");
        }
        out.println("</table>");

        out.println("</div>");



        request.getRequestDispatcher("footer.html").include(request, response);
        out.close();
    }


}
