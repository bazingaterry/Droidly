package com.example.terrychan.lab_4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by terrychan on 18/10/2016.
 */

public class StaticBroadcastActivity extends AppCompatActivity {

    List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.static_broadcast);
        makeList();
        setListView();
    }

    void makeList() {
        fruitList.add(new Fruit("Apple", R.mipmap.apple));
        fruitList.add(new Fruit("Banana", R.mipmap.banana));
        fruitList.add(new Fruit("Cherry", R.mipmap.cherry));
        fruitList.add(new Fruit("Coco", R.mipmap.coco));
        fruitList.add(new Fruit("Kiwi", R.mipmap.kiwi));
        fruitList.add(new Fruit("Orange", R.mipmap.orange));
        fruitList.add(new Fruit("Pear", R.mipmap.pear));
        fruitList.add(new Fruit("Strawberry", R.mipmap.strawberry));
        fruitList.add(new Fruit("Watermelon", R.mipmap.watermelon));
    }

    void setListView() {
        ListView fruitListView = (ListView) findViewById(R.id.static_list);
        fruitListView.setAdapter(new FruitAdapter(fruitList, this));
        fruitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = (Fruit) view.getTag();
                Intent intent = new Intent();
                intent.putExtra("fruit", fruit);
                intent.setAction("com.example.terrychan.STATIC");
                sendBroadcast(intent);
            }
        });
    }
}
