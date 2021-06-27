package com.example.the_health_compass;

import android.graphics.Path;

import androidx.collection.ArrayMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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

    public void Close() {
        try {
            if (!connect.isClosed()) {
                connect.close();
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
                PreparedStatement statement1 = connect.prepareStatement("select Sick_ID from TBLSick where Sick_Email='" + s.Email + "'");
                ResultSet resultSet = statement1.executeQuery();
                if (resultSet.next()) {
                    ConnectionResult = "Successfull";
                }
                PreparedStatement statement2 = connect.prepareStatement("EXEC Insert_Sick_Phone " + resultSet.getString("Sick_ID") + ",'" + s.Phone_Mobile + "'");
                PreparedStatement statement3 = connect.prepareStatement("EXEC Insert_Sick_Age " + resultSet.getString("Sick_ID") + ",'" + s.S_Birthday + "'");
                int rs1 = statement2.executeUpdate();
                int rs2 = statement3.executeUpdate();
                if (rs == 1 & rs1 == 1 & rs2 == 1) {
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
                        String query1 = "select * from TBLSickAge where Sick_ID=" + rs.getString(1) + ";";
                        Statement stat1 = connect.createStatement();
                        ResultSet rs1 = stat1.executeQuery(query1);
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
                        if (rs1.next()) {
                            sick.S_Birthday = rs1.getString(2);
                            sick.InputAge();
                        }
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

    public String getDoctors(ArrayList<ListDoctor> Doctors) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String query = "select * from TBLDoctor;";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                while (rs.next()) {
                    boolean check = Doctors.add(new ListDoctor(rs.getString("Doctor_Full_Name")));

                    ConnectionResult = "Successfull";
                    isSucces = true;
                }
                Close();
                return "Doctors";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }

    public String getIllnessName(ArrayList<String> Illness) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String query = "select ILLnessState_Name from TBLILLnessState;";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                while (rs.next()) {
                    boolean check = Illness.add(rs.getString("ILLnessState_Name"));

                    ConnectionResult = "Successfull";
                    isSucces = true;
                }
                Close();
                return "Doctors";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }

    public String getPartOfBody(ArrayMap<String, String> Partofbody) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String query = "select * from TBLPart_Of_Body;";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                while (rs.next()) {
                    Partofbody.put(rs.getString("Part_Of_Body_ID"), rs.getString("Part_Of_Body_Name"));

                    ConnectionResult = "Successfull";
                    isSucces = true;
                }
                Close();
                return "part of body";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }

    public String getPartOfBodysyle(ArrayMap<String, String> Partofbodystyle, String PartofbodyID) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String query = "select * from TBLPart_Of_Body_Style where Part_Of_Body_ID = " + PartofbodyID + ";";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                while (rs.next()) {

                    Partofbodystyle.put(rs.getString("Part_Of_Body_Style_ID"), rs.getString("Part_Of_Body_Style_Name"));

                    ConnectionResult = "Successfull";
                    isSucces = true;
                }
                Close();
                return "part of body";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }

    public String getSyndromeIl(ArrayMap<String, String> syndromeil, String PartofbodystyleID) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String query = "select * from Have_P_S where Part_Of_Body_Style_ID = " + PartofbodystyleID + ";";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                while (rs.next()) {
                    String query1 = "select * from TBLSyndrome_IL where Syndrome_IL_ID = " + rs.getString(2) + ";";
                    Statement stat1 = connect.createStatement();
                    ResultSet rs1 = stat1.executeQuery(query1);
                    while (rs1.next()) {
                        syndromeil.put(rs1.getString(1), rs1.getString(3));
                    }
                }
                ConnectionResult = "Successfull";
                isSucces = true;
                Close();
                return "part of body";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }

    public String getDiagnos(ArrayMap<String, Integer> Diagnos, String PartofbodystyleID, String PartofbodyID, ArrayList<String> keysyndrome) {
        try {
            String d = "";
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String id_diagnos_by_part_and_style = "select * from TBLDiagnose where Part_Of_Body_Style_ID = " + PartofbodystyleID + " and Part_Of_Body_ID = " + PartofbodyID + ";";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(id_diagnos_by_part_and_style);
                for (int i = 0; i < keysyndrome.size(); i++) {
                    String id_diagnos_by_syndorme = "select Diagnose_ID from Belongs_D_S where Syndrome_IL_ID = " + keysyndrome.get(i) + " ;";
                    Statement stat1 = connect.createStatement();
                    ResultSet rs1 = stat1.executeQuery(id_diagnos_by_syndorme);
                    while (rs1.next()) {
                        if (Diagnos.isEmpty()) {
                            Diagnos.put(rs1.getString(1), 1);
                        } else if (Diagnos.containsKey(rs1.getString(1))) {
                            Diagnos.replace(rs1.getString(1), Diagnos.get(rs1.getString(1)), Diagnos.get(rs1.getString(1)) + 1);
                        } else {
                            Diagnos.put(rs1.getString(1), 1);
                        }
                    }
                }
                int max = 0;
                for (int i = 0; i < Diagnos.size(); i++) {
                    if (Diagnos.valueAt(i) > max) {
                        max = Diagnos.valueAt(i);
                        d = Diagnos.keyAt(i);
                    }
                }
                while (rs.next()) {
                    if (rs.getString(1) == d) {
                        ConnectionResult = "Successfull";
                        isSucces = true;
                        d = rs.getString(3);
                        Close();
                        return d;
                    }
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }

    public boolean SetHospital(Hospital H) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                PreparedStatement statement = connect.prepareStatement("ExEc Insert_Hospital" + H.ID + ",'" + H.H_Name + "','" + H.H_Phone + "','" + H.H_Description + "'");
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

    public String getHospital(String Name, String Phone) {
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                String query = "select * from TBLHospital where H_Name='" + Name + "' and H_Phone = '" + Phone + "'";
                Statement stat = connect.createStatement();
                ResultSet set = stat.executeQuery(query);
                if (set.next()) {
                    ConnectionResult = "Successfull";
                    isSucces = true;
                    connect.close();
                    return "Hospital";
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
    public String getHospitals(ArrayList<ListHospital> Hospitals){
        try {
            Open();
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                //Get Information Sick By UserName
                String query = "select * from TBLDoctor;";
                Statement stat = connect.createStatement();
                ResultSet rs = stat.executeQuery(query);
                while (rs.next()) {
                    boolean check = Hospitals.add(new ListHospital(rs.getString("H_Name"),rs.getString("H_Image"),rs.getString("H_Phone")));
                    ConnectionResult = "Successfull";
                    isSucces = true;
                }
                Close();
                return "Hospitals";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            isSucces = false;
            ConnectionResult = throwables.getMessage();
        }
        return "Not Found";
    }
}
