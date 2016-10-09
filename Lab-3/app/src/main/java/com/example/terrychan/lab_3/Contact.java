package com.example.terrychan.lab_3;

/**
 * Created by terrychan on 10/9/16.
 */

public class Contact {

    private String name, tel, type, location, color;
    private boolean star;

    public Contact(String data) {
        String[] data_split = data.split("\t");
        name = data_split[0];
        tel = data_split[1];
        type = data_split[2];
        location = data_split[3];
        color = data_split[4];
        star = false;
    }
}
