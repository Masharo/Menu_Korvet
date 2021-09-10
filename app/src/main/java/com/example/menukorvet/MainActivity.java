package com.example.menukorvet;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.menukorvet.data.MainViewModel;
import com.example.menukorvet.data.MenuItem;
import com.example.menukorvet.utils.JSONUtils;
import com.example.menukorvet.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private static List<MenuItem> menus;
    private LiveData<List<MenuItem>> liveData;
    private RecyclerView menuABK1;
    private SwipeRefreshLayout layout;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuABK1 = findViewById(R.id.recyclerview_main_menuabk1);
        layout = findViewById(R.id.refreshlayout_main_root);

        menus = new ArrayList<>();
        adapter = new MenuAdapter(this, menus);
        viewModel = ViewModelProvider
                    .AndroidViewModelFactory
                    .getInstance(getApplication())
                    .create(MainViewModel.class);
        menuABK1.setAdapter(adapter);

        layout.setOnRefreshListener(() -> {
            downloadAndUpdateData();
            layout.setRefreshing(false);
        });

        liveDataInstallABK(1);
        downloadAndUpdateData();
    }

    private void liveDataInstallABK(int number) {
        liveData.removeObservers(this);

        viewModel.setABKMenus(number);
        liveData = viewModel.getMenus();
        liveData.observe(this, data -> adapter.setMenus(data));
    }

    private void downloadAndUpdateData() {
        JSONObject json = NetworkUtils.loadData();
        List<MenuItem> menuItems = JSONUtils.jsonToListMenu(json);

        if (Objects.nonNull(menuItems) && !menuItems.isEmpty()) {
            viewModel.deleteAllMenu();

            for (MenuItem menu : menuItems) {
                viewModel.insertMenu(menu);
            }
        } else {
            Toast.makeText(this, "Не удалось обновить данные", Toast.LENGTH_SHORT).show();
        }
    }

    private void installLiveData() {
        liveData = viewModel.getMenus();
        liveData.observe(this, data -> adapter.setMenus(data));

    }
}