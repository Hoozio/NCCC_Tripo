package com.exam.administrator.nccc_trip;

import android.graphics.Bitmap;


/**
 * Created by Administrator on 2017-07-09.
 */

public class TourItem{

    private String name;
    private String address;
    private Bitmap img;
    private int contInt;

    public TourItem(String name, String address, Bitmap img, int contInt) {
        this.name = name;
        this.address = address;
        this.img = img;
        this.contInt = contInt;
    }

    public TourItem(String name, Bitmap img, int contInt) {
        this.name = name;
        this.img = img;
        this.contInt = contInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getContInt() {
        return contInt;
    }

    public void setContInt(int contInt) {
        this.contInt = contInt;
    }

}