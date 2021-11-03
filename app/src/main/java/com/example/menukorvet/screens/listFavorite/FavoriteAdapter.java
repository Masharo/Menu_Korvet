package com.example.menukorvet.screens.listFavorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.DishAndFavorite;
import com.example.menukorvet.pojo.FavoriteAndPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<FavoriteAndPrice> favorites;

    public FavoriteAdapter(List<FavoriteAndPrice> favorites) {
        this.favorites = favorites;
    }

    public FavoriteAdapter() {
        this(new ArrayList<>());
    }

    public void setFavorites(List<FavoriteAndPrice> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteAndPrice favoriteAndPrice = favorites.get(position);

        holder.title.setText(favoriteAndPrice.getFavorites().get(0).getName());
        holder.price.setText(favoriteAndPrice.getFavorites().get(0).getZena());
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private TextView title,
                         price;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textview_menuitem_title);
            price = itemView.findViewById(R.id.textview_menuitem_price);
        }
    }
}
