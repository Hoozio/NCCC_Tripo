package com.exam.administrator.nccc_trip;

/**
 * Created by user on 2017-07-15.
 */

public interface ItemTouchHelperListener {
    boolean onItemMove(int fromPosition, int toPosition);
    void  onItemRemove(int postion);
}
