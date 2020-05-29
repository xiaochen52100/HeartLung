package com.example.qi378.heartlung.bean;

public class UserName {
    public String username;
    public boolean check;
    public UserName(){
        username="";
        check=false;
    }
    public UserName(String username, boolean check){
        this.username=username;
        this.check=check;
    }
}
