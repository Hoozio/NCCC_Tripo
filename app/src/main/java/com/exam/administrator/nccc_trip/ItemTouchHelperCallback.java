package com.exam.administrator.nccc_trip;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;



public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    ItemTouchHelperListener listener;

    public ItemTouchHelperCallback(ItemTouchHelperListener listner){
        this.listener =listner;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFalgs = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlags, swipeFalgs);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target){
        return listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
        listener.onItemRemove(viewHolder.getAdapterPosition());
    }



}