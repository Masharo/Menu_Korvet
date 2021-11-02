package com.example.menukorvet.screens.listFavorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.DishAndFavorite;

import java.util.List;
import java.util.zip.Inflater;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<DishAndFavorite> favorites;

    public FavoriteAdapter(List<DishAndFavorite> favorites) {
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
        DishAndFavorite dishAndFavorite = favorites.get(position);

        holder.title.setText(dishAndFavorite.getName());
        holder.price.setText(dishAndFavorite.getZena());
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
