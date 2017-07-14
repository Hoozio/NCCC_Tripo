package com.exam.administrator.nccc_trip;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017-07-15.
 */

public class CourseItem {
    Bitmap img;
    String name;
    String info;

    public CourseItem(Bitmap img, String name, String info) {
        this.img = img;
        this.name = name;
        this.info = info;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}