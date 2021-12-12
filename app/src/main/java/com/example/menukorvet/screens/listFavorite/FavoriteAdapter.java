package com.example.menukorvet.screens.listFavorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.FavoriteAndPrice;
import com.example.menukorvet.pojo.FavoriteDish;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<FavoriteDish> favorites;

    public FavoriteAdapter(List<FavoriteDish> favorites) {
        this.favorites = favorites;
    }

    public FavoriteAdapter() {
        this(new ArrayList<>());
    }

    public void setFavorites(List<FavoriteDish> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteDish favoriteDish = favorites.get(position);

        holder.title.setText(favoriteDish.getNameFavorite());
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textview_menuitem_title);
        }
    }
}
