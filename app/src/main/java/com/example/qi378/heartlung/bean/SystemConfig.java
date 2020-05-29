package com.example.qi378.heartlung.bean;

public class SystemConfig {
    public String userId,userPasswd;
    public Boolean loginFlag;
    public SystemConfig(){
        userId="";
        userPasswd="";
        loginFlag=false;
    }
    public SystemConfig(String userId,String userPasswd,Boolean loginFlag ){
        this.userId=userId;
        this.userPasswd=userPasswd;
        this.loginFlag=loginFlag;
    }
}
