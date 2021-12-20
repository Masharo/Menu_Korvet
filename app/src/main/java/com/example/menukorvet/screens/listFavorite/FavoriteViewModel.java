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
    private MutableLiveData<List<FavoriteDish>> favorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        database = MenuDatabase.getInstance(application);
        favorites = new MutableLiveData<>(new ArrayList<>());

        updateFavorite();
    }

    public LiveData<List<FavoriteDish>> getFavorites() {
        return favorites;
    }

    public void updateFavorite() {
        try {
            List<FavoriteDish> favoriteDishes = new GetFavoriteTask().execute().get();

            if (Objects.nonNull(favoriteDishes)) {
                favorites.setValue(favoriteDishes);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class GetFavoriteTask extends AsyncTask<Void, Void, List<FavoriteDish>> {

        @Override
        protected List<FavoriteDish> doInBackground(Void... voids) {
            return database.getMenuDao().getFavorites();
        }
    }
}
