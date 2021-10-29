package com.example.menukorvet.screens.listMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menukorvet.R;
import com.example.menukorvet.pojo.DishAndFavorite;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<DishAndFavorite> menus;
    private LayoutInflater inflater;
    private String patternPrice;
    private Context context;

    public MenuAdapter(Context context, List<DishAndFavorite> menus) {
        this.inflater = LayoutInflater.from(context);
        this.menus = menus;
        this.context = context;

        patternPrice = context.getString(R.string.text_price_patternprice);
    }

    public MenuAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMenus(List<DishAndFavorite> menus) {
        this.menus = menus;
        this.notifyDataSetChanged();
    }

    public List<DishAndFavorite> getMenus() {
        return menus;
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        DishAndFavorite menu = menus.get(position);

        holder.title.setText(menu.getName());
        holder.price.setText(String.format(patternPrice, menu.getZena()));

        if (menu.isFavorites()) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.good_green));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView title,
                         price;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textview_menuitem_title);
            price = itemView.findViewById(R.id.textview_menuitem_price);
        }
    }
}
