package com.example.menukorvet;

import static androidx.recyclerview.widget.ItemTouchHelper.START;
import static androidx.recyclerview.widget.ItemTouchHelper.END;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

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
    private RecyclerView menuABK1;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuABK1 = findViewById(R.id.recyclerview_main_menuabk1);
        menus = new ArrayList<>();
        adapter = new MenuAdapter(this, menus);
        viewModel = ViewModelProvider
                    .AndroidViewModelFactory
                    .getInstance(getApplication())
                    .create(MainViewModel.class);
        menuABK1.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, START | END) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

//        touchHelper.

        installLiveData();
        downloadData();
    }

    private void downloadData() {
        JSONObject json = NetworkUtils.loadData();
        List<MenuItem> menuItems = JSONUtils.jsonToListMenu(json);

        if (Objects.nonNull(menuItems) && !menuItems.isEmpty()) {
            viewModel.deleteAllMenu();

            for (MenuItem menu : menuItems) {
                viewModel.insertMenu(menu);
            }
        }
    }

    private void installLiveData() {
        LiveData<List<MenuItem>> liveData = viewModel.getMenus();
        liveData.observe(this, data -> adapter.setMenus(data));
    }
}