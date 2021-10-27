package com.example.menukorvet.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_dish")
public class FavoriteDish {

    @PrimaryKey(autoGenerate = true)
    private int favoriteDishId;
    private String nameFavorite;

    @Ignore
    public FavoriteDish(String nameFavorite) {
        this.nameFavorite = nameFavorite;
    }

    public FavoriteDish() {};

    public int getFavoriteDishId() {
        return favoriteDishId;
    }

    public void setFavoriteDishId(int favoriteDishId) {
        this.favoriteDishId = favoriteDishId;
    }

    public String getNameFavorite() {
        return nameFavorite;
    }

    public void setNameFavorite(String nameFavorite) {
        this.nameFavorite = nameFavorite;
    }
}
