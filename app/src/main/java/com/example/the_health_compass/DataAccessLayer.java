package com.example.the_health_compass;

import android.graphics.Path;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.prefs.PreferenceChangeEvent;

public class DataAccessLayer {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSucces = false;
    ConnectionHelper connectionHelper;

    public DataAccessLayer() {
        connectionHelper = new ConnectionHelper();
        connect = connectionHelper.connections();
    }

    public void Open() {
        try {
            if (connect.isClosed()) {
                connect = connectionHelper.connections();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean SetDoctor(Doctor D) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                PreparedStatement statement = connect.prepareStatement("EXEc Insert_Doctor " + D.ID + ",'" + D.D_Full_Name + "'," +
                        "'" + D.Email + "','" + D.Password + "','" + D.D_Location + "','" + D.Subscription + "'," +
                        "'" + D.Check_Email + "','" + D.Blocking + "','" + D.Personal_Image + "'," +
                        "'" + D.D_Gender + "'");
                int rs = statement.executeUpdate();
                if (rs == 1) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return true;
                } else {
                    connect.close();
                    return false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return false;
    }

    public String getDoctor(String UserName, String Email, String Password) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                String query = "select * from TBLDoctor where Doctor_Full_Name='" + UserName + "' and Doctor_Password='" + Password + "';";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "User";
                }
                String query1 = "select * from TBLDoctor where Doctor_Email='" + Email + "';";
                stat = connect.createStatement();
                rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "Email";
                }
                String query2 = "select * from TBLDoctor where Doctor_Password='" + Password + "';";
                stat = connect.createStatement();
                rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "Password";
                } else {
                    connect.close();
                    return "Not Found";
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }

    //Set Sick By Prosedure Insert_Sick
    public boolean SetSick(Sick s) {
        try {
            Open();
            //Check The Connection Successfull By Internet
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {

                PreparedStatement statement = connect.prepareStatement("EXEC Insert_Sick " + s.ID + ",'" + s.S_Full_Name + "'," +
                        "'" + s.Email + "','" + s.Password + "','" + s.S_Location + "','" + s.Subscription + "'," +
                        "'" + s.Check_Email + "','" + s.Blocking + "','" + s.Personal_Image + "'," +
                        "'" + s.S_Gender + "'");
                int rs = statement.executeUpdate();
                if (rs == 1) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return true;
                } else {
                    connect.close();
                    return false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return false;
    }

    // Get Information Sick By UserName or Email or Password
    public String getsick(String UserName, String Email, String Password) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String query = "select * from TBLSick where Sick_Full_Name='" + UserName + "';";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "User";
                }
                //Get Information Sick By Email
                String query1 = "select * from TBLSick where Sick_Email='" + Email + "';";
                stat = connect.createStatement();
                rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "Email";
                }
                //Get Information Sick By Password
                String query2 = "select * from TBLSick where Sick_Password='" + Password + "';";
                stat = connect.createStatement();
                rs = stat.executeQuery(query);
                if (rs.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "Password";
                } else {
                    connect.close();
                    return "Not Found";
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }

    public String check_sick_sign_in(String UserName, String
            Password, AtomicReference<Sick> s) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String query = "select * from TBLSick where Sick_Full_Name='" + UserName + "' and Sick_Password='" + Password + "';";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                if (rs.next()) {
                    Sick sick = new Sick();
                    try {
                        sick.ID = rs.getString(1);
                        sick.S_Full_Name = rs.getString(2);
                        sick.Email = rs.getString(3);
                        sick.Password = rs.getString(4);
                        sick.S_Location = rs.getString(5);
                        sick.Subscription = rs.getString(6);
                        sick.Check_Email = rs.getString(7);
                        sick.Blocking = rs.getBoolean(8);
                        sick.Personal_Image = rs.getString(9);
                        sick.Creat_Date = rs.getString(10);
                        sick.S_Gender = rs.getString(11);
                        s.set(sick);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "User";
                } else {
                    connect.close();
                    return "Not Found";
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Faild Access!";
    }
}
