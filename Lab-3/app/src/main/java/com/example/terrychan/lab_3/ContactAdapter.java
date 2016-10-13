package com.example.terrychan.lab_3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by terrychan on 10/9/16.
 */

public class ContactAdapter extends BaseAdapter {
    private List<Contact> contacts;
    private Context context;

    public ContactAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup view_group) {
        view = View.inflate(this.context, R.layout.contact_element, null);
        TextView contact_first_letter = (TextView) view.findViewById(R.id.contact_first_letter);
        contact_first_letter.setText(this.contacts.get(i).getFirstLetter());
        TextView name = (TextView) view.findViewById(R.id.contact_name);
        name.setText(this.contacts.get(i).getName());
        return view;
    }
}
