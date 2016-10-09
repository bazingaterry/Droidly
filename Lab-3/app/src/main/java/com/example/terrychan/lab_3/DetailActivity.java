package com.example.terrychan.lab_3;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by terrychan on 09/10/2016.
 */

public class DetailActivity extends AppCompatActivity {
    private String[] contact_operation_string = new String[]{
            "编辑联系人",
            "分享联系人",
            "加入黑名单",
            "删除联系人"
    };
    private ListView contact_operation;
    private TextView name, tel, type, location;
    private Contact contact;
    private ImageView back, star;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        setView();
        setText();
        setClickStarResponse();
        setBackResponse();
        contact_operation.setAdapter(new OperationAdapter(contact_operation_string, this));
    }

    private void setView() {
        contact_operation = (ListView) findViewById(R.id.contact_operation);
        name = (TextView) findViewById(R.id.name);
        tel = (TextView) findViewById(R.id.tel);
        type = (TextView) findViewById(R.id.type);
        location = (TextView) findViewById(R.id.location);
        contact = (Contact) getIntent().getSerializableExtra("contact");
        back = (ImageView) findViewById(R.id.back);
        star = (ImageView) findViewById(R.id.star);
    }

    private void setText() {
        name.setText(contact.getName());
        tel.setText(contact.getTel());
        type.setText(contact.getType());
        location.setText(contact.getLocation());
        if (contact.isStar())
            star.setImageResource(R.mipmap.full_star);
        else
            star.setImageResource(R.mipmap.empty_star);
    }

    private void setClickStarResponse() {
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact.setStar(!contact.isStar());
                if (contact.isStar())
                    star.setImageResource(R.mipmap.full_star);
                else
                    star.setImageResource(R.mipmap.empty_star);
            }
        });
    }

    private void setBackResponse() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
