package com.example.terrychan.lab_4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by terrychan on 18/10/2016.
 */

public class FruitAdapter extends BaseAdapter {
    List<Fruit> fruitList;
    Context context;

    public FruitAdapter(List<Fruit> fruitList, Context context) {
        this.fruitList = fruitList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return fruitList.size();
    }

    @Override
    public Object getItem(int position) {
        return fruitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(this.context, R.layout.list_item, null);
        ImageView img = (ImageView) convertView.findViewById(R.id.item_image);
        TextView text = (TextView) convertView.findViewById(R.id.item_text);
        convertView.setTag(fruitList.get(position));
        img.setImageResource(fruitList.get(position).getImg());
        text.setText(fruitList.get(position).getName());
        return convertView;
    }
}
