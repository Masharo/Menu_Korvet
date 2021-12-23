package com.example.menukorvet.screens.listFavorite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.FavoriteAndPrice;
import com.example.menukorvet.pojo.FavoriteDish;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteAdapter adapter;
    private RecyclerView favoritesUI;
    private FavoriteViewModel viewModel;
    private LiveData<List<FavoriteDish>> favoritesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        adapter = new FavoriteAdapter();
        favoritesUI = findViewById(R.id.recyclerview_favorite_listfavorite);
        favoritesUI.setAdapter(adapter);

        viewModel =  ViewModelProvider
                    .AndroidViewModelFactory
                    .getInstance(getApplication())
                    .create(FavoriteViewModel.class);

        viewModel.getFavorites().observe(FavoriteActivity.this, data -> {
            adapter.setFavorites(data);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        }).attachToRecyclerView(favoritesUI);

    }
}