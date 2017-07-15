package com.exam.administrator.nccc_trip;

/**
 * Created by Administrator on 2017-07-15.
 */

public class CourseDetailData {
    String num;
    String name;


    public CourseDetailData(String num, String name) {
        this.num = num;
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
