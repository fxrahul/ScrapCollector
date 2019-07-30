package com.example.scrapcollector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by singh on 6/11/2017.
 */

public class CustomAdapter extends ArrayAdapter<String> {
    ArrayList<String> quantityy;
    ArrayList<String> waste_typee;
    ArrayList<String> namee;
    public CustomAdapter(Context context, ArrayList<String> quantity, ArrayList<String> code, ArrayList<String> name, ArrayList<String> w_type) {
        super(context,R.layout.custon_row,code);
        quantityy=quantity;
        waste_typee=w_type;
        namee=name;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater= LayoutInflater.from(getContext());
        View customview=myinflater.inflate(R.layout.custon_row,parent,false);
        TextView quantity=(TextView) customview.findViewById(R.id.quantity);
        TextView name=(TextView) customview.findViewById(R.id.name);
        TextView waste_type=(TextView) customview.findViewById(R.id.waste_type);
        String codee=getItem(position);
        name.setText(namee.get(position));
        quantity.setText(quantityy.get(position));
        waste_type.setText(waste_typee.get(position));
        return  customview;
    }
}
