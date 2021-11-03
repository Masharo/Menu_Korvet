package com.example.menukorvet.pojo;

import androidx.room.Relation;

import java.util.List;

public class DishAndFavorite {

    private int id;
    private String name;
    private int zena;
    private int abk;

    @Relation(parentColumn = "name", entityColumn = "nameFavorite")
    public List<FavoriteDish> favorites;

    public List<FavoriteDish> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoriteDish> favorites) {
        this.favorites = favorites;
    }

    public boolean isFavorites() {
        return getFavorites().size() > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
