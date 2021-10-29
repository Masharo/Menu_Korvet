package com.example.menukorvet.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_dish")
public class FavoriteDish {

    @NonNull
    @PrimaryKey
    private String nameFavorite;

    @Ignore
    public FavoriteDish(String nameFavorite) {
        this.nameFavorite = nameFavorite;
    }

    public FavoriteDish() {};

    public String getNameFavorite() {
        return nameFavorite;
    }

    public void setNameFavorite(String nameFavorite) {
        this.nameFavorite = nameFavorite;
    }
}
