package com.example.terrychan.lab_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button staticButton = (Button) findViewById(R.id.static_broadcast),
                dynamicButton = (Button) findViewById(R.id.dynamic_broadcast);
        staticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StaticBroadcastActivity.class);
                startActivity(intent);
            }
        });
        dynamicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DynamicBroadcastActivity.class);
                startActivity(intent);
            }
        });
    }
}
