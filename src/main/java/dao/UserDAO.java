package dao;


import beans.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static int save(User user){
        int status=0;
        try {
            Connection con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("insert into elibrary.user (name,email,password,mobile) " +
                            "values(?,?,?,?)");
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPassword());
            ps.setLong(4,user.getMobile());
            status=ps.executeUpdate();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static int update(User user){
        int status=0;
        try{

            try (Connection con = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            , "root", "Dalia-7100")) {
                PreparedStatement ps=con.prepareStatement
                        ("update elibrary.user set name=?,email=?,password=?," +
                                "mobile=? where id=?");
                ps.setString(1,user.getName());
                ps.setString(2,user.getEmail());
                ps.setString(3,user.getPassword());
                ps.setLong(4,user.getMobile());
                ps.setInt(5,user.getId());
                status=ps.executeUpdate();
                con.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }


    public static List<User> view(){

        List<User> list=new ArrayList<User>();
        try{
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement("select * from elibrary.user");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                User bean=new User();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                bean.setEmail(rs.getString("email"));
                bean.setPassword(rs.getString("password"));
                bean.setMobile(rs.getLong("mobile"));
                list.add(bean);
            }
            con.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public static User viewById(int id){
        User user=new User();
        try{
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("select * from elibrary.user where id=?");
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

    public static int delete(int id){

        int status=0;
        try{
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("delete from elibrary.user where id=?");
            ps.setInt(1,id);
            status=ps.executeUpdate();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static boolean authenticate(String email,String password){

        boolean status=false;
        try{
            Connection con=DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/eLibrary"
                            ,"root","Dalia-7100");
            PreparedStatement ps=con.prepareStatement
                    ("select * from elibrary.user where email=? and password=?");
            ps.setString(1,email);
            ps.setString(2,password);
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

}
