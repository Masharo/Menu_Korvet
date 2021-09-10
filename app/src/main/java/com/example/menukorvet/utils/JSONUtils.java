package com.example.menukorvet.utils;

import com.example.menukorvet.Exception.MenuException;
import com.example.menukorvet.data.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JSONUtils {

    private static final String  KEY_DATA = "Data",
                                KEY_NAME = "name",
                                KEY_PRICE = "zena",
                                KEY_ABK = "abk";

    public static List<MenuItem> jsonToListMenu(JSONObject json) {

        if (Objects.isNull(json)) {
            return null;
        }

        ArrayList<MenuItem> menus = new ArrayList<>();

        try {
            JSONArray array = json.getJSONArray(KEY_DATA);
            for (int i = 0; i < array.length(); i++) {
                JSONObject rowJSON = array.getJSONObject(i);

                MenuItem menuItem = new MenuItem(
                        rowJSON.getInt(KEY_ABK),
                        rowJSON.getInt(KEY_PRICE),
                        rowJSON.getString(KEY_NAME));

                menus.add(menuItem);
            }
        } catch (JSONException e) {
            MenuException.logException(JSONUtils.class, e);
        }

        return menus;
    }
}
