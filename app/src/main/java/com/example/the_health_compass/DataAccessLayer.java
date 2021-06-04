package com.example.the_health_compass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAccessLayer {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSucces=false;
    public DataAccessLayer(){
        ConnectionHelper connectionHelper = new ConnectionHelper();
        connect = connectionHelper.connections();
    }
    public boolean SetSick(Sick s){
        try {
            if (connect==null){
                ConnectionResult="Check Your Internet Access!";
            }
            else{
                PreparedStatement statement = connect.prepareStatement("EXEC Insert_Sick "+s.ID+",'"+s.S_Full_Name+"'," +
                        "'"+s.Email+"','"+s.Password+"','"+s.S_Location+"','"+s.Subscription+"',"+
                        "'"+s.Check_Email+"','"+s.Blocking+"','"+s.Personal_Image+"'," +
                        "'"+s.S_Gender+"'");
                int rs = statement.executeUpdate();
                if (rs == 1) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return true;
                }
                else {
                    connect.close();
                    return false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult=throwables.getMessage();
        }
        return false;
    }
    public String getsick(String UserName,String Email,String Password){
        try {
            if (connect==null){
                ConnectionResult="Check Your Internet Access!";
            }
            else{
                String query = "select * from TBLSick where Sick_Full_Name='"+UserName+"';";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "User";
                }
                String query1 = "select * from TBLSick where Sick_Email='"+Email+"';";
                stat = connect.createStatement();
                rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "Email";
                }
                String query2 = "select * from TBLSick where Sick_Password='"+Password+"';";
                stat = connect.createStatement();
                rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "Password";
                }
                else {
                    connect.close();
                    return "Not Found";
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult=throwables.getMessage();
        }
        return "Not Found";
    }
}
