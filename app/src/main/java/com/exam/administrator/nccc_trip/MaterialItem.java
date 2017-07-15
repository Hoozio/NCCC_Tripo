package com.exam.administrator.nccc_trip;

import android.view.View;

/**
 * Created by user on 2017-07-13.
 */

public class MaterialItem{
    private String checkTitle;
    private int checkPosition;


    public MaterialItem(String name){
        this.checkTitle = name;
        this.checkPosition = checkPosition;
    }

    public int getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(int checkPosition) {
        this.checkPosition = checkPosition;
    }

    public String getCheckTitle() {
        return checkTitle;
    }

}
