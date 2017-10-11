package com.exam.administrator.nccc_trip;

/**
 * Created by user on 2017-07-13.
 */

public class MaterialItem{
    private String checkTitle;
    private boolean checkPosition;

    public MaterialItem(String name, boolean checkPosition){

        this.checkTitle = name;
        this.checkPosition = checkPosition;
    }

    public boolean getCheckPosition() {
        return checkPosition;
    }

    public void setCheckPosition(boolean checkPosition) {
        this.checkPosition = checkPosition;
    }

    public String getCheckTitle() {
        return checkTitle;
    }

}
