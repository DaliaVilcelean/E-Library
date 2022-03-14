package dao;


import beans.Book;
import beans.BorrowedBook;
import beans.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public static int save(Book book){
        int status=0;
        try{
            Connection con=   DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("insert into elibrary.book values(?,?,?,?,?,?)");
            ps.setString(1,book.getCallno());
            ps.setString(2,book.getName());
            ps.setString(3,book.getAuthor());
            ps.setString(4,book.getPublisher());
            ps.setInt(5,book.getQuantity());
            ps.setInt(6,0);
            status=ps.executeUpdate();
            con.close();


        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static List<Book> view()  {

        List<Book> list=new ArrayList<Book>();
        try {
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement("select * from elibrary.book");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Book bean=new Book();
                bean.setCallno(rs.getString("callno"));
                bean.setName(rs.getString("name"));
                bean.setAuthor(rs.getString("author"));
                bean.setPublisher(rs.getString("publisher"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setIssued(rs.getInt("borrowed"));

                list.add(bean);

            }
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public static int delete(String callno)  {
        int status=0;

        try {
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("delete from book where callno=?");
            ps.setString(1,callno);
            status=ps.executeUpdate();
            con.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static int getBorrowed(String callno){
        int borrowed=0;

        try {
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("select * from book where callno=?");
            ps.setString(1,callno);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                borrowed=rs.getInt("issued");
            }
            con.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return borrowed;
    }

    public static boolean checkIssue(String callno){
        boolean status=false;

        try {
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("select * from elibrary.book where callno=? and quantity>borrowed");
            ps.setString(1,callno);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                status=true;
            }
            con.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static int borrowBook(BorrowedBook book){
        String callno=book.getCallno();
        boolean checkStatus=checkIssue(callno);
        System.out.println("Check status: "+checkStatus);
        if(checkStatus){
            int status=0;
            try {
                Connection con = DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/eLibrary"
                                ,"root","Dalia-7100");
                PreparedStatement ps=con.prepareStatement
                        ("insert into elibrary.borrowedBook values(?,?,?,?,?,?)");
                ps.setString(1,book.getCallno());
                ps.setString(2,book.getStudentid());
                ps.setString(3,book.getStudentname());
                ps.setLong(4,book.getStudentmobile());
                java.sql.Date currentDate=new java.sql.Date(System.currentTimeMillis());
                ps.setDate(5,currentDate);
                ps.setString(6,"no");

                status=ps.executeUpdate();

                if(status>0){
                    PreparedStatement ps2=con.prepareStatement
                            ("update book set borrowed=? where callno=?");
                    ps2.setInt(1,getBorrowed(callno)+1);
                    ps2.setString(2,callno);
                    status=ps2.executeUpdate();
                }
                con.close();


            }  catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return status;
        }else{
            return 0;
        }
    }

    public static int returnBook(String callno,int studentid){

        int status=0;
        try{
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("update elibrary.borrowedBook set returnstatus='yes' where" +
                            " callno=? and studentid=?");
            ps.setString(1,callno);
            ps.setInt(2,studentid);

            status=ps.executeUpdate();
            if(status>0){
                PreparedStatement ps2=con.prepareStatement
                        ("update elibrary.book set borrowed=? where callno=?");
                ps2.setInt(1,getBorrowed(callno)-1);
                ps2.setString(2,callno);
                status=ps2.executeUpdate();
            }
            con.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;

    }

    public static List<BorrowedBook> viewBorrowedBooks(){

        List<BorrowedBook> list=new ArrayList<BorrowedBook>();
        try{
            Connection con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("select * from elibrary.borrowedBook ");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                BorrowedBook bean=new BorrowedBook();
                bean.setCallno(rs.getString("callno"));
                bean.setStudentid(rs.getString("studentid"));
                bean.setStudentname(rs.getString("studentname"));
                bean.setStudentmobile(rs.getLong("mobile"));
                bean.setIssueddate(rs.getDate("issuedate"));
                bean.setReturnstatus(rs.getString("returnstatus"));
                list.add(bean);
            }
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public static int update(User user){
        int status=0;

        try{
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("update elibrary.user set name=?,email=?,password=?,mobile=? where id=?");
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPassword());
            ps.setLong(4,user.getMobile());
            ps.setInt(5,user.getId());
            status=ps.executeUpdate();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;

    }

    public static User viewById(int id){
        User user=new User();
        try{
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("select * from user where id=?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getLong(5));
            }
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
