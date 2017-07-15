package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 2017-07-13.
 */

public class MaterialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<MaterialItem> mItems;


    private int lastPosition = -1;




    public MaterialAdapter(ArrayList items, Context mContext) {
        mItems = items;
        context = mContext;

    }


    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material, parent, false);
        holder = new DeleteItemVH(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DeleteItemVH){
            DeleteItemVH deleteItemVH = (DeleteItemVH)holder;

            deleteItemVH.checkBox.setText(mItems.get(position).getCheckTitle());
            deleteItemVH.deleteButton.setTag(holder);

            deleteItemVH.deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int holderPosition = ((DeleteItemVH)v.getTag()).getAdapterPosition();
                    Log.e("*****", ""+holderPosition);
                    mItems.remove(holderPosition);
                    notifyItemRemoved(holderPosition);
                    notifyItemChanged(holderPosition,  mItems.size());
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }



    public class DeleteItemVH extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
        public Button deleteButton;

        public DeleteItemVH(View view){
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.material_check);
            deleteButton = (Button) view.findViewById(R.id.material_bbutton);
        }
    }
}
