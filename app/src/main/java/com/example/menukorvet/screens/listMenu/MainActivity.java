package com.example.menukorvet.screens.listMenu;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static androidx.recyclerview.widget.ItemTouchHelper.START;
import static androidx.recyclerview.widget.ItemTouchHelper.END;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.DishAndFavorite;
import com.example.menukorvet.pojo.FavoriteDish;
import com.example.menukorvet.supports.ABKController;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private boolean isRequestDeleteAllMenu;
    private MainViewModel viewModel;
    private String titleMenu;
    private RecyclerView menuABK;
    private TextView notMenu;
    private ActionBar actionBar;
    private TextView buttonABK;
    private ABKController abk;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MenuAdapter adapter;
    private boolean lockSwipeRefresherLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuABK = findViewById(R.id.recyclerview_main_menuabk);
        swipeRefreshLayout = findViewById(R.id.refreshlayout_main_root);
        buttonABK = findViewById(R.id.textview_main_buttonabk);
        notMenu = findViewById(R.id.textview_main_notmenu);
        actionBar = getSupportActionBar();
        lockSwipeRefresherLayout = true;

        isRequestDeleteAllMenu = false;
        adapter = new MenuAdapter(this);
        viewModel = ViewModelProvider
                    .AndroidViewModelFactory
                    .getInstance(getApplication())
                    .create(MainViewModel.class);
        menuABK.setAdapter(adapter);
        abk = ABKController.getInstance();
        titleMenu = getString(R.string.text_main_titlemenu);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.loadData(abk.getABK());
        });

        viewModel.getIsRequestDeleteAllMenu().observe(MainActivity.this, isDeleteOperation ->
                isRequestDeleteAllMenu = isDeleteOperation
        );

        viewModel.getMenus().observe(MainActivity.this, menu -> {
            adapter.setMenus(menu);
            listIsEmpty(menu);
            swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.getErrors().observe(MainActivity.this, error -> {
            Toast.makeText(MainActivity.this, R.string.text_main_errornotdata, Toast.LENGTH_SHORT).show();
            listIsEmpty(null);
            swipeRefreshLayout.setRefreshing(false);
        });

        viewModel.loadData(abk.getABK());
        viewInstallABK();

        new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                MenuAdapter.Status status = ((MenuAdapter.MenuViewHolder) viewHolder).getStatus();
                int swipeFlags = status == MenuAdapter.Status.GOOD ? ItemTouchHelper.END : ItemTouchHelper.START;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                DishAndFavorite dishAndFavorite = adapter.getMenus().get(viewHolder.getAdapterPosition());
                int size = dishAndFavorite.getFavorites().size();

                if (direction == START && size == 0) {
                    viewModel.insertFavorite(dishAndFavorite);
                    dishAndFavorite.getFavorites().add(new FavoriteDish(dishAndFavorite.getName()));
                } else if (direction == END && size > 0) {
                    viewModel.deleteFavorite(dishAndFavorite);
                    dishAndFavorite.getFavorites().remove(0);
                }

                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                lockSwipeRefresherLayout = !lockSwipeRefresherLayout;
                swipeRefreshLayout.setEnabled(lockSwipeRefresherLayout);
            }
        }).attachToRecyclerView(menuABK);
    }

    private void listIsEmpty(List<DishAndFavorite> menu) {

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