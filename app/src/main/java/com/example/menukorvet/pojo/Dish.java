package com.example.menukorvet.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "dish")
public class Dish {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("zena")
    @Expose
    private int zena;
    @SerializedName("abk")
    @Expose
    private int abk;

    @Ignore
    public Dish(int id_abk, int price, String title) {

        this.abk = id_abk;
        this.zena = price;
        this.name = title;
    }

    public Dish(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dish(int id, int id_abk, int price, String title) {

        this(id_abk, price, title);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZena() {
        return zena;
    }

    public void setZena(int zena) {
        this.zena = zena;
    }

    public int getAbk() {
        return abk;
    }

    public void setAbk(int abk) {
        this.abk = abk;
    }

}
