package com.example.menukorvet.data;

import androidx.room.Entity;

@Entity(tableName = "menu", primaryKeys = "id")
public class MenuItem {

    private int id,
                id_abk,
                price;
    private String title;

    public int getId() {
        return id;
    }

    public MenuItem(int id, int id_abk, int price, String title) {
        this.id = id;
        this.id_abk = id_abk;
        this.price = price;
        this.title = title;
    }

    public MenuItem(int id_abk, int price, String title) {
        this.id_abk = id_abk;
        this.price = price;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_abk() {
        return id_abk;
    }

    public void setId_abk(int id_abk) {
        this.id_abk = id_abk;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
