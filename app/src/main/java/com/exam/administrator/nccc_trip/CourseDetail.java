package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-15.
 */

public class CourseDetail extends LinearLayout implements View.OnClickListener{

    public TextView num;
    public TextView name;
    CourseDetailData item;
    int contInt;
    int detailInt;

    public CourseDetail(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View v = View.inflate(context, R.layout.item_course_detail, this);
        num = (TextView) v.findViewById(R.id.course_number);
        name = (TextView) v.findViewById(R.id.course_name);
        this.setOnClickListener(this);
        item = new CourseDetailData("1", "코스 제목");
    }

    public void setNum(String num) {
        this.item.setNum(num);
    }

    public void setName(String name) {
        this.item.setName(name);
    }

    public void setItem(CourseDetailData item) {
        this.item = item;
    }

    public String getNum(){
        return this.item.getNum();
    }

    public String getName(){
        return this.item.getName();
    }

    public CourseDetailData getItem(){
        return this.item;
    }
    public void refresh() {
        num.setText(String.valueOf(item.getNum()));
        name.setText(item.getName());
    }
    @Override
    public void onClick(View v){
        Intent i = new Intent(this.getContext(),DetailActivity.class);
        i.putExtra("what",3);
        i.putExtra("detail",getDetailInt());
        i.putExtra("course",getContInt());
        this.getContext().startActivity(i);
    }

    public int getContInt() {
        return contInt;
    }

    public void setContInt(int contInt) {
        this.contInt = contInt;
    }

    public int getDetailInt() {
        return detailInt;
    }

    public void setDetailInt(int detailInt) {
        this.detailInt = detailInt;
    }
}