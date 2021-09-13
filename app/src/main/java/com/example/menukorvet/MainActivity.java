package com.example.menukorvet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
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
            downloadAndUpdateData();
            layout.setRefreshing(false);
        });

        downloadAndUpdateData();
        liveDataInstallABK();
    }

    private void liveDataInstallABK() {
        abk.abkNextInstance();

        String titleABK = getString(abk.getABK().getName());
        buttonABK.setText(abk.getNextABK().getName());
        actionBar.setTitle(String.format(titleMenu, titleABK));

        viewModel.setABKMenus(abk.getABK().getId());
        liveData = viewModel.getMenus();
        liveData.observe(this, data -> {
            adapter.setMenus(data);

            if (data.isEmpty()) {
                notMenu.setVisibility(View.VISIBLE);
            } else {
                notMenu.setVisibility(View.INVISIBLE);
            }
        });
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
            Toast.makeText(this, R.string.text_main_errornotdata, Toast.LENGTH_SHORT).show();
        }
    }

    private void installLiveData() {
        liveData = viewModel.getMenus();
        liveData.observe(this, data -> adapter.setMenus(data));

    }

    public void onClickReselectABK(View view) {
        liveData.removeObservers(this);
        liveDataInstallABK();
    }
}