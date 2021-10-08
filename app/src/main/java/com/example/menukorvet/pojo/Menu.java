package com.example.menukorvet.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Menu {

    @SerializedName("Exec")
    @Expose
    private String exec;
    @SerializedName("Data")
    @Expose
    private List<Dish> data = null;
    @SerializedName("Number")
    @Expose
    private int number;
    @SerializedName("Now")
    @Expose
    private String now;

    public String getExec() {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }

    public List<Dish> getData() {
        return data;
    }

    public void setData(List<Dish> data) {
        this.data = data;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }


}
