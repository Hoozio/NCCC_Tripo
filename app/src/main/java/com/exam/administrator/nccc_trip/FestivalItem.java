package com.exam.administrator.nccc_trip;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017-07-24.
 */

public class FestivalItem {

    String name;
    String schedule;
    Bitmap img;
    int contId;

    public FestivalItem(String name, String schedule, Bitmap img, int contId) {
        this.name = name;
        this.schedule = schedule;
        this.img = img;
        this.contId = contId;
    }

    public FestivalItem(String name, String schedule, Bitmap img) {
        this.name = name;
        this.schedule = schedule;
        this.img = img;

    }

    public FestivalItem(String name, Bitmap img) {
        this.name = name;
        this.img = img;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getContId() {
        return contId;
    }

    public void setContId(int contId) {
        this.contId = contId;
    }
}