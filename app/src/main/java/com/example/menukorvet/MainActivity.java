package com.example.menukorvet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menukorvet.data.MainViewModel;
import com.example.menukorvet.data.MenuItem;

import java.util.ArrayList;
import java.util.List;

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
        viewModel = ViewModelProvider
                    .AndroidViewModelFactory
                    .getInstance(getApplication())
                    .create(MainViewModel.class);
        menus = new ArrayList<>();
        adapter = new MenuAdapter(this, menus);
    }

    private void installLiveData() {
        LiveData<List<MenuItem>> liveData = viewModel.getMenus();
        liveData.observe(this, adapter::setMenus);

    }
}