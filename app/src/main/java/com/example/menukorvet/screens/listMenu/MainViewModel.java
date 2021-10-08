package com.example.menukorvet.screens.listMenu;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.menukorvet.api.ApiFactory;
import com.example.menukorvet.data.MenuDatabase;
import com.example.menukorvet.pojo.Dish;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    //isRequestDeleteAllMenu - указывает на то, что БД в какойто момент будет очищена и заполнена снова
    //чтобы по середине операции не считалось что БД пуста
    private static MutableLiveData<Boolean> isRequestDeleteAllMenu;
    private static MenuDatabase database;
    private LiveData<List<Dish>> menus;
    private MutableLiveData<Throwable> errors;
    private CompositeDisposable compositeDisposable;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = MenuDatabase.getInstance(getApplication());
        menus = database.getMenuDao().getAllMenu();
        compositeDisposable = new CompositeDisposable();
        errors = new MutableLiveData<>();
        isRequestDeleteAllMenu = new MutableLiveData<>();
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public LiveData<List<Dish>> getMenus() {
        return menus;
    }

    public MutableLiveData<Boolean> getIsRequestDeleteAllMenu() {
        return isRequestDeleteAllMenu;
    }

    public void setABKMenus(int numberABK) {
        menus = database.getMenuDao().getAllMenuABK(numberABK);
    }

    public void setABKMenus() {
        menus = database.getMenuDao().getAllMenu();
    }

    public void insertMenu(List<Dish> menu) {
        new InsertMenuTask().execute(menu);
    }

    public void deleteAllMenu() {
        new DeleteAllMenuTask().execute();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    private static class InsertMenuTask extends AsyncTask<List<Dish>, Void, Void> {

        @Override
        protected Void doInBackground(List<Dish>... menus) {
            if (Objects.nonNull(menus) && menus.length > 0) {
                database.getMenuDao().insertMenu(menus[0]);
            }

            return null;
        }
    }

    public void loadData() {
        compositeDisposable.add(
                ApiFactory.getInstance().getApiService().getCanteen("canteen", "web_list", "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menu -> {
                            isRequestDeleteAllMenu.setValue(true);
                            deleteAllMenu();
                            insertMenu(menu.getData());
                        },
                        throwable -> {
                            errors.setValue(throwable);
                        })
        );
    }

    private static class DeleteAllMenuTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.getMenuDao().clearAllMenu();

            return null;
        }
    }
}
