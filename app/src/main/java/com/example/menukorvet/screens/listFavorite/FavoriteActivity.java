package com.example.menukorvet.screens.listFavorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.FavoriteDish;

import java.util.List;
import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteAdapter adapter;
    private RecyclerView favoritesUI;
    private FavoriteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoritesUI = findViewById(R.id.recyclerview_favorite_listfavorite);

        viewModel =  ViewModelProvider
                    .AndroidViewModelFactory
                    .getInstance(getApplication())
                    .create(FavoriteViewModel.class);

        adapter = new FavoriteAdapter(FavoriteActivity.this, viewModel.getFavorites());
        favoritesUI.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final List<FavoriteDish> favoritesList = viewModel.getFavorites().getValue();

                if (Objects.nonNull(favoritesList)) {
                    viewModel.deleteFavorite(favoritesList.get(viewHolder.getAdapterPosition()));
                }
            }
        }).attachToRecyclerView(favoritesUI);
    }
}