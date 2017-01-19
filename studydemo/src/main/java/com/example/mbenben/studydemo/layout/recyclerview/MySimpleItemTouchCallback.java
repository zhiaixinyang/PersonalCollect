package com.example.mbenben.studydemo.layout.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.mbenben.studydemo.layout.recyclerview.adapter.RecyclerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by MBENBEN on 2017/1/14.
 */

public class MySimpleItemTouchCallback extends ItemTouchHelper.Callback {

    private RecyclerAdapter adapter;
    private List<String> datas;
    public MySimpleItemTouchCallback(RecyclerAdapter adapter, List<String> data){
        this.adapter = adapter;
        datas = data;
    }
    /**
     * 设置支持的拖拽、滑动的方向
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlag,swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(datas, from, to);
        adapter.notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        datas.remove(pos);
        adapter.notifyItemRemoved(pos);
    }


    /**
     * 状态改变时回调
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.i("Callback", actionState + "");
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            RecyclerAdapter.ViewHolder holder = (RecyclerAdapter.ViewHolder)viewHolder;
            holder.itemView.setBackgroundColor(0xffbcbcbc);
        }
    }

    /**
     * 拖拽或滑动完成之后调用，用来清除一些状态
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        RecyclerAdapter.ViewHolder holder = (RecyclerAdapter.ViewHolder)viewHolder;
        holder.itemView.setBackgroundColor(0xffeeeeee);
    }
}
