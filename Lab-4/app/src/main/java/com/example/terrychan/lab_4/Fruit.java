package com.example.terrychan.lab_4;

import java.io.Serializable;

/**
 * Created by terrychan on 18/10/2016.
 */

public class Fruit implements Serializable{
    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    private String name;
    private int img;

    public Fruit(String name, int img) {
        this.name = name;
        this.img = img;
    }
}
