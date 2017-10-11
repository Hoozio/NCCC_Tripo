package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-07-15.
 */

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<CourseItem> mItems;
    private int lastPosition = -1;

    public CourseAdapter(ArrayList items, Context mContext) {
        mItems = items;
        context = mContext;


    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    // 필수로 Generate 되어야 하는 메소드 1 : 새로운 뷰 생성
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 새로운 뷰를 만든다
        RecyclerView.ViewHolder holder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        holder = new CourseViewHolder(v);
        return holder;
    }

    // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CourseViewHolder)holder).img.setImageBitmap(mItems.get(position).getImg());
        ((CourseViewHolder)holder).subject.setText(mItems.get(position).getSubject());
        ((CourseViewHolder)holder).info.setText(Html.fromHtml(mItems.get(position).getInfo()));
        ((CourseViewHolder)holder).container.removeAllViewsInLayout();
        for(int i=0; i<mItems.get(position).getCount();i++){
            CourseDetail detail = new CourseDetail(context);
            detail.setNum(mItems.get(position).getCourseItems().get(i).getNum());
            detail.setName(mItems.get(position).getCourseItems().get(i).getName());
            detail.setDetailInt(mItems.get(position).getCourseItems().get(i).getDetailInt());
            detail.setContInt(mItems.get(position).getCourseItems().get(i).getContInt());
            detail.setLon(mItems.get(position).getCourseItems().get(i).getLon());
            detail.setLat(mItems.get(position).getCourseItems().get(i).getLat());
            detail.refresh();
            ((CourseViewHolder)holder).container.addView(detail);
        }
    }

    // 필수로 Generate 되어야 하는 메소드 3
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public final static class CourseViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView subject;
        public TextView info;
        FlexboxLayout container;
        public CourseViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.course_img);
            subject = (TextView) view.findViewById(R.id.course_subject);
            info = (TextView) view.findViewById(R.id.course_info);
            container = (FlexboxLayout)view.findViewById(R.id.course_detail_inside);

        }
    }
}