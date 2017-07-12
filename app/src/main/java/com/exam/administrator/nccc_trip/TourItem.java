package com.exam.administrator.nccc_trip;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Administrator on 2017-07-09.
 */

public class TourItem implements Parcelable {

    private String name;
    private String address;
    private Bitmap img;
    private int contInt;


    public TourItem(Parcel src) {
        readFromParcel(src);
    }

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contInt);
    }

    private void readFromParcel(Parcel src) {
        this.contInt = src.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TourItem createFromParcel(Parcel in) {
            return new TourItem(in);
        }
        public TourItem[] newArray(int size) {
            return new TourItem[size];
        }
    };
}