package com.example.menukorvet.screens.listFavorite;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.menukorvet.data.MenuDatabase;
import com.example.menukorvet.pojo.FavoriteAndPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteViewModel extends AndroidViewModel {

    private static MenuDatabase database;
    private MutableLiveData<List<FavoriteAndPrice>> favorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        database = MenuDatabase.getInstance(application);
        favorites.setValue(new ArrayList<>());
    }

    public LiveData<List<FavoriteAndPrice>> getFavorites() {
        return favorites;
    }

    public void getFavorite() {
        try {
            favorites.setValue(new GetFavoriteTask().execute().get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class GetFavoriteTask extends AsyncTask<Void, Void, List<FavoriteAndPrice>> {

        @Override
        protected List<FavoriteAndPrice> doInBackground(Void... voids) {
            return database.getMenuDao().getFavorites();
        }
    }
}
