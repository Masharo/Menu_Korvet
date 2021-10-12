package com.example.menukorvet.screens.listMenu;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.Dish;
import com.example.menukorvet.supports.ABKController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private boolean isRequestDeleteAllMenu;
    private MainViewModel viewModel;
    private static List<Dish> menus;
    private String titleMenu;
    private RecyclerView menuABK;
    private TextView notMenu;
    private ActionBar actionBar;
    private TextView buttonABK;
    private ABKController abk;
    private SwipeRefreshLayout layout;
    private MenuAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuABK = findViewById(R.id.recyclerview_main_menuabk);
        layout = findViewById(R.id.refreshlayout_main_root);
        buttonABK = findViewById(R.id.textview_main_buttonabk);
        notMenu = findViewById(R.id.textview_main_notmenu);
        actionBar = getSupportActionBar();

        isRequestDeleteAllMenu = false;
        menus = new ArrayList<>();
        adapter = new MenuAdapter(this, menus);
        viewModel = ViewModelProvider
                    .AndroidViewModelFactory
                    .getInstance(getApplication())
                    .create(MainViewModel.class);
        menuABK.setAdapter(adapter);
        abk = ABKController.getInstance();
        titleMenu = getString(R.string.text_main_titlemenu);

        layout.setOnRefreshListener(() -> {
            viewModel.loadData(abk.getABK());
        });

        viewModel.getIsRequestDeleteAllMenu().observe(MainActivity.this, isDeleteOperation ->
                isRequestDeleteAllMenu = isDeleteOperation
        );

        viewModel.getMenus().observe(MainActivity.this, menu -> {
            adapter.setMenus(menu);
            listIsEmpty(menu);
            layout.setRefreshing(false);
        });

        viewModel.getErrors().observe(MainActivity.this, error -> {
            Toast.makeText(MainActivity.this, R.string.text_main_errornotdata, Toast.LENGTH_SHORT).show();
            listIsEmpty(null);
            layout.setRefreshing(false);
        });

        viewModel.loadData(abk.getABK());
        viewInstallABK();
    }

    private void listIsEmpty(List<Dish> menu) {

        int length = adapter.getItemCount();

        if ((Objects.isNull(menu) && length == 0) || (!isRequestDeleteAllMenu && Objects.nonNull(menu) && menu.size() == 0)) {
            notMenu.setVisibility(View.VISIBLE);
        } else {
            notMenu.setVisibility(View.INVISIBLE);
        }

        isRequestDeleteAllMenu = false;
    }

    private void viewInstallABK() {
        String titleABK = getString(abk.getABK().getName());
        buttonABK.setText(abk.getNextABK().getName());
        actionBar.setTitle(String.format(titleMenu, titleABK));
    }

    public void onClickReselectABK(View view) {
        abk.abkNextInstance();
        viewInstallABK();
        viewModel.setABKMenus(abk.getABK());
    }
}