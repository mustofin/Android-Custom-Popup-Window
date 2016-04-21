package com.mustofin.custompopupwindow;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean MENU_SHOW = false;
    public static String TITLE = "title";
    public static String BACKGROUND = "background";

    ListPopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MENU NAVIGATION
        popupWindow = new ListPopupWindow(MainActivity.this);
        String[] from = {TITLE};
        int[] to = {R.id.item_popupwindow_text};

        popupWindow.setAdapter(new MenuNavigationAdapter(MainActivity.this, getMenus(),
                R.layout.item_popupwindow, from, to));

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        popupWindow.setWidth(displaymetrics.widthPixels); // note: don't use pixels, use a dimen resource

        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MENU_SHOW) {
                    popupWindow.dismiss();
                    MENU_SHOW = false;
                } else {
                    popupWindow.setAnchorView(v);
                    popupWindow.show();
                    MENU_SHOW = true;
                }
            }
        });
//        END MENU NAVIGATION
    }

    public ArrayList getMenus(){
        ArrayList<HashMap<String, String>> menus = new ArrayList<HashMap<String, String>>();
        menus.add(getMenuItemColorString("Home", "#9249CD"));
        menus.add(getMenuItemColorString("Category", "#e74c3c"));
        menus.add(getMenuItemColorString("Portfolio", "#16a085"));
        menus.add(getMenuItemColorString("About", "#2980b9"));
        menus.add(getMenuItemColorString("Contact", "#f1c40f"));
        menus.add(getMenuItemColorString("Gallery", "#d35400"));
        menus.add(getMenuItemColorString("Detil", "#7f8c8d"));
        menus.add(getMenuItemColorInt("Ihir", R.color.colorAccent));
        menus.add(getMenuItemColorInt("Uh Oh", R.color.colorPrimary));
        return menus;
    }

    private HashMap getMenuItemColorInt (String menuTitle, int colorId){
        HashMap<String, String> menu = new HashMap<String, String>();
        menu.put(TITLE, menuTitle);
        String color = "#"+Integer.toHexString(getResources().getColor(colorId));
        menu.put(BACKGROUND, color);
        return menu;
    }

    private HashMap getMenuItemColorString (String menuTitle, String colorString){
        HashMap<String, String> menu = new HashMap<String, String>();
        menu.put(TITLE, menuTitle);
        menu.put(BACKGROUND, colorString);
        return menu;
    }


    class MenuNavigationAdapter extends SimpleAdapter {
        Context context;
        List<HashMap<String, String>> menus = new ArrayList<HashMap<String, String>>();


        public MenuNavigationAdapter(Context context, List<HashMap<String, String>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            menus = data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.item_popupwindow, parent, false);
            TextView titleItemMenu = (TextView) rowView.findViewById(R.id.item_popupwindow_text);
            final String itemMenuTitle = menus.get(position).get(MainActivity.TITLE);
            titleItemMenu.setText(itemMenuTitle);

            RelativeLayout item_menu_nav_layout = (RelativeLayout) rowView.findViewById(R.id.item_popupwindow_container);
            String background = menus.get(position).get(MainActivity.BACKGROUND);
            item_menu_nav_layout.setBackgroundColor(Color.parseColor(background));

            item_menu_nav_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "MENU \""+itemMenuTitle+"\" CLICKED", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
            });

            return rowView;
        }
    }
}
