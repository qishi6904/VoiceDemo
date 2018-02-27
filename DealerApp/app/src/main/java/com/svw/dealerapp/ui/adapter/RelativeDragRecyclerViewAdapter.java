package com.svw.dealerapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.svw.dealerapp.ui.widget.DragFrontRelativeLayout;
import com.svw.dealerapp.ui.widget.DragLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 5/17/2017.
 */

public abstract class RelativeDragRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    protected List<DragRelativeRecyclerViewHolder> holderList = new ArrayList<>();
    protected DragLayout currentOpenLayout;

    public RelativeDragRecyclerViewAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    /**
     * 设置左滑条目右边部分的监听，处理事件拦截，到达左滑一个条目，触碰其他条目，已经左滑的条目关闭左滑
     * @param holder
     */
    protected void setHolderListener(final DragRelativeRecyclerViewHolder holder){
        holder.frontLayout.setOnInterceptListener(new DragFrontRelativeLayout.OnInterceptListener(){
            @Override
            public void onIntercept() {
                if(null != currentOpenLayout){
                    currentOpenLayout.close();
                    currentOpenLayout = null;
                }
            }
        });

        holder.dragLayout.setOnStatusChangeListener(new DragLayout.OnStatusChangeListener() {
            @Override
            public void onOpen() {
                for(int i = 0; i < holderList.size(); i++){
                    holderList.get(i).frontLayout.setIsInterceptTouchEvent(true);
                }
                currentOpenLayout = holder.dragLayout;
            }

            @Override
            public void onClose() {
                for(int i = 0; i < holderList.size(); i++){
                    holderList.get(i).frontLayout.setIsInterceptTouchEvent(false);
                }
                currentOpenLayout = null;
            }

            @Override
            public void isMoveStatus(boolean isMoveStatus) {
                if(isMoveStatus) {
                    for (int i = 0; i < holderList.size(); i++) {
                        if (holderList.get(i) == holder) {
                            holderList.get(i).frontLayout.setIsInterceptTouchEvent(false);
                        } else {
                            holderList.get(i).frontLayout.setIsInterceptTouchEvent(true);
                        }
                    }
                    currentOpenLayout = holder.dragLayout;
                }
            }
        });
        holderList.add(holder);
    }

    public class DragRelativeRecyclerViewHolder extends RecyclerView.ViewHolder{

        public DragLayout dragLayout;
        public DragFrontRelativeLayout frontLayout;

        public DragRelativeRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void clearIntercept(){
        for(int i = 0; i < holderList.size(); i++){
            holderList.get(i).frontLayout.setIsInterceptTouchEvent(false);
            currentOpenLayout = null;
        }
    }
}
