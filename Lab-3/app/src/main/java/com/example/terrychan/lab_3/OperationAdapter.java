package com.example.terrychan.lab_3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by terrychan on 09/10/2016.
 */

public class OperationAdapter extends BaseAdapter {
    private String[] operations;
    private Context context;

    public OperationAdapter(String[] operations, Context context) {
        this.operations = operations;
        this.context = context;
    }

    @Override
    public int getCount() {
        return operations.length;
    }

    @Override
    public Object getItem(int i) {
        return operations[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(context, R.layout.contact_operation, null);
        TextView operation = (TextView) view.findViewById(R.id.operation);
        operation.setText(operations[i]);
        return view;
    }
}