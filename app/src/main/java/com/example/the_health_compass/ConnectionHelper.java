package com.example.the_health_compass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

public class ConnectionHelper {
    String IP,DB,DBUserName,DBPassword,port;
    @SuppressLint("NewApi")
    //Found DataBase and Format the Connection
    public Connection connections(){
        //Information the connection
        IP = "208.118.63.167";
        DB = "db_a75982_tofiqdaoud";
        DBUserName = "db_a75982_tofiqdaoud_admin";
        DBPassword = "1q2w3e4r5t6y7u";
        port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        java.sql.Connection connection = null;
        String Connectionurl = null;

        try {
            //Format The Connection
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Connectionurl = "jdbc:jtds:sqlserver://" + IP + ":" + port+ ";databaseName=" + DB + ";user=" + DBUserName + ";password=" + DBPassword + ";";
            connection = DriverManager.getConnection(Connectionurl);
        } catch (ClassNotFoundException se) {
            Log.e("error form Class",se.getMessage());
        } catch (SQLException sa) {
            Log.e("error from SQL",sa.getMessage());
        }
        catch (Exception ex) {
            Log.e("Error from Exception", ex.getMessage());
        }
        return connection;
    }
}
