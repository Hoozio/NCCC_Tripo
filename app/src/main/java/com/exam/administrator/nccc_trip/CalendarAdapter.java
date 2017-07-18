package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-17.
 */
public class CalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<TourItem> mItems;
    private int lastPosition = -1;

    public CalendarAdapter(ArrayList items, Context mContext) {
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
        View v;
        RecyclerView.ViewHolder holder = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        holder = new CalendarViewHolder(v);
        return holder;
    }

    // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CalendarViewHolder)holder).name.setText(mItems.get(position).getName());
        ((CalendarViewHolder)holder).address.setText(mItems.get(position).getAddress());
        ((CalendarViewHolder)holder).image.setImageBitmap(mItems.get(position).getImg());
    }

    // 필수로 Generate 되어야 하는 메소드 3
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public final static class CalendarViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView medal;
        public TextView address;
        public TextView name;
        public TextView distance;
        public CalendarViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.calendar_img);
            address = (TextView) view.findViewById(R.id.calendar_address);
            distance = (TextView) view.findViewById(R.id.calendar_distance);
            name = (TextView) view.findViewById(R.id.calendar_name);
        }
    }
}