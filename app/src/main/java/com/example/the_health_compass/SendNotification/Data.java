package com.example.the_health_compass.SendNotification;

public class Data {
    private String Title;
    private String User;
    private String Message;
    private String token;
    public Data(String title,String message,String user,String token){
        this.Title = title;
        this.Message = message;
        this.User = user;
        this.token = token;
    }
    public Data(){

    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return Message;
    }

    public String getTitle() {
        return Title;
    }

    public String getUser() {
        return User;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setUser(String user) {
        User = user;
    }
}
