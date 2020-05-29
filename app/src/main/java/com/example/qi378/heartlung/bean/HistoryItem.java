package com.example.qi378.heartlung.bean;

public class HistoryItem {
    public String time,value;
    public HistoryItem(){
        time="";
        value="";
    }
    public HistoryItem(String time, String value){
        this.time=time;
        this.value=value;
    }
}
