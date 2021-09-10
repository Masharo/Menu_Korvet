package com.example.menukorvet.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Objects;

public class MainViewModel extends AndroidViewModel {

    private static MenuDatabase database;
    private LiveData<List<MenuItem>> menus;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = MenuDatabase.getInstance(getApplication());
        menus = database.getMenuDao().getAllMenu();
    }

    public LiveData<List<MenuItem>> getMenus() {
        return menus;
    }

    public void setABKMenus(int numberABK) {
        menus = database.getMenuDao().getAllMenuABK(numberABK);
    }

    public void setABKMenus() {
        menus = database.getMenuDao().getAllMenu();
    }

    public void insertMenu(MenuItem menu) {
        new InsertMenuTask().execute(menu);
    }

    public void deleteAllMenu() {
        new DeleteAllMenuTask().execute();
    }

    private static class InsertMenuTask extends AsyncTask<MenuItem, Void, Void> {

        @Override
        protected Void doInBackground(MenuItem... menus) {
            if (Objects.nonNull(menus) && menus.length > 0) {
                database.getMenuDao().insertMenu(menus[0]);
            }

            return null;
        }
    }

    private static class DeleteAllMenuTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.getMenuDao().clearAllMenu();

            return null;
        }
    }
}
