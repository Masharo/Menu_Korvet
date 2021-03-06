package com.example.menukorvet.screens.listFavorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.menukorvet.data.MenuDatabase;
import com.example.menukorvet.pojo.FavoriteDish;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FavoriteViewModel extends AndroidViewModel {

    private static MenuDatabase database;
    private LiveData<List<FavoriteDish>> favorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        database = MenuDatabase.getInstance(application);
        loadFavorite();
    }

    public LiveData<List<FavoriteDish>> getFavorites() {
        return favorites;
    }

    public void loadFavorite() {
        try {
            @NonNull
            LiveData<List<FavoriteDish>> favoriteDishes = new GetFavoriteTask().execute().get();

            favorites = favoriteDishes;

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteFavorite(FavoriteDish favoriteDish) {
        new DeleteItemFavoriteTask().execute(favoriteDish);
    }

    private static class GetFavoriteTask extends AsyncTask<Void, Void, LiveData<List<FavoriteDish>>> {

        @Override
        protected LiveData<List<FavoriteDish>> doInBackground(Void... voids) {
            return database.getMenuDao().getFavorites();
        }
    }

    private static class DeleteItemFavoriteTask extends AsyncTask<FavoriteDish, Void, Void> {

        @Override
        protected Void doInBackground(FavoriteDish... favoriteDish) {

            if (Objects.nonNull(favoriteDish) && favoriteDish.length > 0) {
                database.getMenuDao().deleteFavorite(favoriteDish[0]);
            }

            return null;
        }
    }
}
