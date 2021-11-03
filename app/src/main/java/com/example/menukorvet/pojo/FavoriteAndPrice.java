package com.example.menukorvet.pojo;

import androidx.room.Relation;

import java.util.List;

public class FavoriteAndPrice {

    private String nameFavorite;

    @Relation(parentColumn = "nameFavorite", entityColumn = "name")
    public List<Dish> favorites;

    public String getNameFavorite() {
        return nameFavorite;
    }

    public void setNameFavorite(String nameFavorite) {
        this.nameFavorite = nameFavorite;
    }

    public List<Dish> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Dish> favorites) {
        this.favorites = favorites;
    }
}
