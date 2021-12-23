package com.example.menukorvet.screens.listFavorite;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.FavoriteDish;

import java.util.List;
import java.util.Objects;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private LiveData<List<FavoriteDish>> favorites;

    @SuppressLint("NotifyDataSetChanged")
    public FavoriteAdapter(@NonNull LifecycleOwner lifecycleOwner, @NonNull LiveData<List<FavoriteDish>> favorites) {
        this.favorites = favorites;
        favorites.observe(lifecycleOwner, data ->
            notifyDataSetChanged()
        );
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteDish favoriteDish = favorites.getValue().get(position);
        holder.title.setText(favoriteDish.getNameFavorite());
    }

    @Override
    public int getItemCount() {
        return Objects.nonNull(favorites.getValue()) ? favorites.getValue().size() : 0;
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textview_favorites_item);
        }
    }
}
