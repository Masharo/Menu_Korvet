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
import com.example.menukorvet.pojo.Dish;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<Dish> menus;
    private LayoutInflater inflater;
    private String patternPrice;

    public MenuAdapter(Context context, List<Dish> menus) {
        this.inflater = LayoutInflater.from(context);
        this.menus = menus;

        patternPrice = context.getString(R.string.text_price_patternprice);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMenus(List<Dish> menus) {
        this.menus = menus;
        this.notifyDataSetChanged();
    }

    public List<Dish> getMenus() {
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
        Dish menu = menus.get(position);

        holder.title.setText(menu.getName());
        holder.price.setText(String.format(patternPrice, menu.getZena()));
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
