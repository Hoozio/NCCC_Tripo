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
public class FestivalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<FestivalItem> mItems;
    private int lastPosition = -1;

    public FestivalAdapter(ArrayList items, Context mContext) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_festival, parent, false);
        RecyclerView.ViewHolder holder = new FestivalViewHolder(v);
        return holder;
    }

    // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((FestivalViewHolder)holder).name.setText(mItems.get(position).getName());
        ((FestivalViewHolder)holder).schedule.setText(mItems.get(position).getSchedule());
        ((FestivalViewHolder)holder).image.setImageBitmap(mItems.get(position).getImg());
    }

    // 필수로 Generate 되어야 하는 메소드 3
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public final static class FestivalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView schedule;
        public TextView name;
        public FestivalViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.festival_img);
            schedule = (TextView) view.findViewById(R.id.festival_schedule);
            name = (TextView) view.findViewById(R.id.festival_name);
        }
    }
}