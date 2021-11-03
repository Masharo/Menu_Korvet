package com.example.menukorvet.screens.listFavorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.FavoriteAndPrice;
import com.example.menukorvet.pojo.FavoriteDish;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteAdapter adapter;
    private RecyclerView favoritesUI;
    private FavoriteViewModel viewModel;
    private LiveData<List<FavoriteAndPrice>> favoritesData;

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

        favoritesData = viewModel.getFavorites();

        favoritesData.observe(this, data -> {
            adapter.setFavorites(data);
        });
    }
}