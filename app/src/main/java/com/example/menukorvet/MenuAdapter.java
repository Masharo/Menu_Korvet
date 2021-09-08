package com.example.menukorvet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menukorvet.data.MenuItem;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {

    private List<MenuItem> menus;
    private LayoutInflater inflater;

    public MenuAdapter(Context context, List<MenuItem> menus) {
        this.inflater = LayoutInflater.from(context);
        this.menus = menus;
    }

    public void setMenus(List<MenuItem> menus) {
        this.menus = menus;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.menu_item, parent);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MenuItem menu = menus.get(position);

        MenuViewHolder menuHolder = (MenuViewHolder) holder;

        menuHolder.title.setText(menu.getTitle());
        menuHolder.price.setText(menu.getPrice());
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
