package com.exam.administrator.nccc_trip;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-07-15.
 */

public class CourseItem {
    Bitmap img;
    String subject;
    String info;
    ArrayList<CourseDetail> courseItems;
    int count;
    int contInt;

    public CourseItem(Bitmap img, String subject, String info, ArrayList<CourseDetail> courseItems, int count) {
        this.img = img;
        this.subject = subject;
        this.info = info;
        this.courseItems = courseItems;
        this.count = count;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<CourseDetail> getCourseItems() {
        return courseItems;
    }

    public void setCourseItems(ArrayList<CourseDetail> courseItems) {
        this.courseItems = courseItems;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getContInt() {
        return contInt;
    }

    public void setContInt(int contInt) {
        this.contInt = contInt;
    }
}