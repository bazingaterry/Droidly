package com.example.terrychan.lab_3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> contacts = new ArrayList<>();
    private ListView list_view;
    private ContactAdapter contact_adapter;

    private void setContactListView() {
        contacts.add(new Contact("Aaron\t17715523654\t手机\t江苏苏州电信\tBB4C3B"));
        contacts.add(new Contact("Elvis\t18825653224\t手机\t广东揭阳移动\tc48d30"));
        contacts.add(new Contact("David\t15052116654\t手机\t江苏无锡移动\t4469b0"));
        contacts.add(new Contact("Edwin\t18854211875\t手机\t山东青岛移动\t20A17B"));
        contacts.add(new Contact("Frank\t13955188541\t手机\t安徽合肥移动\tBB4C3B"));
        contacts.add(new Contact("Joshua\t13621574410\t手机\t江苏苏州移动\tc48d30"));
        contacts.add(new Contact("Ivan\t15684122771\t手机\t山东烟台联通\t4469b0"));
        contacts.add(new Contact("Mark\t17765213579\t手机\t广东珠海电信\t20A17B"));
        contacts.add(new Contact("Joseph\t13315466578\t手机\t河北石家庄电信\tBB4C3B"));
        contacts.add(new Contact("Phoebe\t17895466428\t手机\t山东东营移动\tc48d30"));
        this.contact_adapter = new ContactAdapter(contacts, this);
        this.list_view = (ListView) findViewById(R.id.contact_list);
        this.list_view.setAdapter(this.contact_adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContactListView();
        setLongPressResponse();
        setClickResponse();
    }

    private void setLongPressResponse() {
        this.list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDeleteDialog(i);
                return true;
            }
        });
    }

    void showDeleteDialog(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case AlertDialog.BUTTON_POSITIVE:
                        contacts.remove(id);
                        contact_adapter.notifyDataSetChanged();
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        builder.setTitle("提示").setMessage("确定删除联系人 " + contacts.get(id).getName() + "？")
                .setTitle("删除联系人")
                .setPositiveButton("确定", onClickListener).setNegativeButton("取消", onClickListener)
                .create().show();
    }

    void setClickResponse() {
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = contacts.get(i);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
            }
        });
    }
}
