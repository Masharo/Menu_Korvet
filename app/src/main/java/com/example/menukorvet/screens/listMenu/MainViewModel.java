package com.example.menukorvet.screens.listMenu;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.menukorvet.api.ApiFactory;
import com.example.menukorvet.data.MenuDatabase;
import com.example.menukorvet.pojo.Dish;
import com.example.menukorvet.supports.ABKController;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    //isRequestDeleteAllMenu - указывает на то, что БД в какойто момент будет очищена и заполнена снова
    //чтобы по середине операции не считалось что БД пуста
    private static MutableLiveData<Boolean> isRequestDeleteAllMenu;
    private static MenuDatabase database;
    private final MutableLiveData<List<Dish>> menus;
    private final MutableLiveData<Throwable> errors;
    private final CompositeDisposable compositeDisposable;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = MenuDatabase.getInstance(getApplication());
        menus = new MutableLiveData<>();
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

    public LiveData<Boolean> getIsRequestDeleteAllMenu() {
        return isRequestDeleteAllMenu;
    }

    public void setABKMenus(ABKController.NumberABK numberABK) {
        menus.setValue(getAllMenusABK(numberABK));
    }

    public List<Dish> getAllMenus() {
        List<Dish> dishes = null;

        try {
            dishes = new GetAllMenuTask().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return dishes;
    }

    public List<Dish> getAllMenusABK(ABKController.NumberABK numberABK) {
        List<Dish> dishes = null;

        try {
            dishes = new GetAllMenuABKTask().execute(numberABK).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return dishes;
    }

    public void setABKMenus() {
        menus.setValue(getAllMenus());
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
        protected synchronized Void doInBackground(List<Dish>... menus) {
            if (Objects.nonNull(menus) && menus.length > 0) {
                database.getMenuDao().insertMenu(menus[0]);
            }

            return null;
        }
    }

    public void loadData(ABKController.NumberABK numberABK) {

        compositeDisposable.add(
                ApiFactory.getInstance().getApiService().getCanteen("canteen", "web_list", "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menu -> {
                            isRequestDeleteAllMenu.setValue(true);
                            deleteAllMenu();
                            insertMenu(menu.getData());
                            setABKMenus(numberABK);//Если будет находиться в конце метода то будет выполнено до завершения загрузки
                        },
                        throwable -> {
                            errors.setValue(throwable);
                            setABKMenus(numberABK);
                        })
        );
    }

    private static class GetAllMenuTask extends AsyncTask<Void, Void, List<Dish>> {

        @Override
        protected synchronized List<Dish> doInBackground(Void... voids) {

            return database.getMenuDao().getAllMenu();
        }
    }

    private static class GetAllMenuABKTask extends AsyncTask<ABKController.NumberABK, Void, List<Dish>> {

        @Override
        protected synchronized List<Dish> doInBackground(ABKController.NumberABK... numberABKS) {
            List<Dish> dishes = null;

            if (Objects.nonNull(numberABKS) && numberABKS.length > 0) {
                dishes = database.getMenuDao().getAllMenuABK(numberABKS[0].getId());
            }

            return dishes;
        }
    }

    private static class DeleteAllMenuTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected synchronized Void doInBackground(Void... voids) {
            database.getMenuDao().clearAllMenu();

            return null;
        }
    }
}
