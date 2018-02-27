package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.mine.adapter.MineRecyclerViewAdapter;
import com.svw.dealerapp.ui.resource.entity.ResourceFilterItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 5/12/2017.
 */

public class ResourceFilterAdapter extends Adapter {

    private List<ResourceFilterItemEntity> itemStringList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private boolean isMultiSelect;  //是否是多选
    private boolean hasAll;
    private Holder lastHolder;
    private List<Holder> multiSelectHolderList;
    private List<Holder> holderList = new ArrayList<>();

    public ResourceFilterAdapter(Context context, List<ResourceFilterItemEntity> list, boolean isMultiSelect, boolean hasAll){
        this.context = context;
        this.itemStringList = list;
        this.isMultiSelect = isMultiSelect;
        this.hasAll = hasAll;
        if(isMultiSelect){
            multiSelectHolderList = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(context, R.layout.item_resource_filter_recycler_view, null);
        Holder holder = new Holder(relativeLayout);
        holderList.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Holder viewHolder = (Holder) holder;
        ResourceFilterItemEntity filterItemEntity = itemStringList.get(position);
        viewHolder.textView.setText(filterItemEntity.getName());

        //全部按钮初始化时默认选中
        if(filterItemEntity.isInitSelect() || viewHolder.isSelect){
            viewHolder.textView.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.shape_resource_filter_item_select_bg));
            viewHolder.textView.setTextColor(Color.WHITE);
            if(filterItemEntity.isInitSelect()) {
                filterItemEntity.setInitSelect(false);
                viewHolder.isSelect = true;
                if(isMultiSelect){
                    multiSelectHolderList.add(viewHolder);
                }
                lastHolder = viewHolder;
            }
        }else {
            viewHolder.textView.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
            viewHolder.textView.setTextColor(context.getResources()
                    .getColor(R.color.resource_main_text));
        }

        viewHolder.isAllItem = filterItemEntity.isAll();

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMultiSelect){      //多选
                    dealMultiItemClick(viewHolder, position);
                }else {     //单选
                    dealSingleItemClick(viewHolder);
                }
                viewHolder.isSelect = !viewHolder.isSelect;
                if(null != onItemClickListener){
                    onItemClickListener.onItemClick(v, itemStringList.get(position), isMultiSelect, viewHolder.isSelect);
                }
            }
        });
    }

    /**
     * 处理单选条目的点击事件
     * @param viewHolder
     */
    private void dealSingleItemClick(Holder viewHolder){
        if(viewHolder.isSelect){    //当前是选中

            if(!hasAll) {
                return;
            }

            viewHolder.textView.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
            viewHolder.textView.setTextColor(context.getResources()
                    .getColor(R.color.resource_main_text));

            Holder allHolder = holderList.get(0);
            allHolder.textView.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.shape_resource_filter_item_select_bg));
            allHolder.textView.setTextColor(Color.WHITE);
            allHolder.isSelect = true;
            lastHolder = allHolder;

        }else {     //当前未选中
            if(null != lastHolder && viewHolder != lastHolder){
                lastHolder.textView.setBackgroundDrawable(context.getResources()
                        .getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
                lastHolder.textView.setTextColor(context.getResources()
                        .getColor(R.color.resource_main_text));
                lastHolder.isSelect = false;
            }
            viewHolder.textView.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.shape_resource_filter_item_select_bg));
            viewHolder.textView.setTextColor(Color.WHITE);
            lastHolder = viewHolder;
        }
    }

    /**
     * 处理多选条目的点击事件
     * @param viewHolder
     * @param position
     */
    private void dealMultiItemClick(Holder viewHolder, int position){
        if(itemStringList.get(position).isAll()) {   //如果点击的是全部
            viewHolder.isAllItem = true;
            if(viewHolder.isSelect){
//                viewHolder.textView.setBackgroundDrawable(context.getResources()
//                        .getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
//                viewHolder.textView.setTextColor(context.getResources()
//                        .getColor(R.color.resource_main_text));
                return;
            }else {
                for (int i = 0; i < multiSelectHolderList.size(); i++) {
                    Holder selectHolder = multiSelectHolderList.get(i);
                    selectHolder.textView.setBackgroundDrawable(context.getResources()
                            .getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
                    selectHolder.textView.setTextColor(context.getResources()
                            .getColor(R.color.resource_main_text));
                    selectHolder.isSelect = false;
                }

                viewHolder.textView.setBackgroundDrawable(context.getResources()
                        .getDrawable(R.drawable.shape_resource_filter_item_select_bg));
                viewHolder.textView.setTextColor(Color.WHITE);

                multiSelectHolderList.clear();
                multiSelectHolderList.add(viewHolder);
            }
        }else {     //如果点击的是其他
            if(viewHolder.isSelect){
                viewHolder.textView.setBackgroundDrawable(context.getResources()
                        .getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
                viewHolder.textView.setTextColor(context.getResources()
                        .getColor(R.color.resource_main_text));

                multiSelectHolderList.remove(viewHolder);
                if (multiSelectHolderList.size() == 0){
                    Holder allHolder = holderList.get(0);
                    allHolder.textView.setBackgroundDrawable(context.getResources()
                            .getDrawable(R.drawable.shape_resource_filter_item_select_bg));
                    allHolder.textView.setTextColor(Color.WHITE);
                    allHolder.isSelect = true;
                    multiSelectHolderList.add(allHolder);
                }

            }else {
                boolean isContainerAllItem = false;     //是否包含全部的item
                for (int i = 0; i < multiSelectHolderList.size(); i++) {
                    if (multiSelectHolderList.get(i).isAllItem) {
                        isContainerAllItem = true;
                        break;
                    }
                }
                if (isContainerAllItem) {
                    for (int i = 0; i < multiSelectHolderList.size(); i++) {
                        Holder selectHolder = multiSelectHolderList.get(i);
                        selectHolder.textView.setBackgroundDrawable(context.getResources()
                                .getDrawable(R.drawable.shape_resource_filter_item_unselect_bg));
                        selectHolder.textView.setTextColor(context.getResources()
                                .getColor(R.color.resource_main_text));
                        selectHolder.isSelect = false;
                    }
                    multiSelectHolderList.clear();
                }
                viewHolder.textView.setBackgroundDrawable(context.getResources()
                        .getDrawable(R.drawable.shape_resource_filter_item_select_bg));
                viewHolder.textView.setTextColor(Color.WHITE);
                multiSelectHolderList.add(viewHolder);
            }
            viewHolder.isAllItem = false;
        }
    }

    @Override
    public int getItemCount() {
        return null == itemStringList ? 0 : itemStringList.size();
    }

    private class Holder extends RecyclerView.ViewHolder{

        TextView textView;
        boolean isSelect = false;
        boolean isAllItem = false;   //是否是全部的holder

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_traffic_filter_item);
        }
    }

    public interface OnItemClickListener{
        /**
         *
         * @param view
         * @param entity
         * @param isMultiSelect  是否是多选
         * @param isSelect       true为被选中，false为被取消选中
         */
        void onItemClick(View view, ResourceFilterItemEntity entity, boolean isMultiSelect, boolean isSelect);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 清空选中的holder
     */
    public void clearSelectHolder(){
        if(null != multiSelectHolderList && multiSelectHolderList.size() > 0){
            for(int i = 0; i < multiSelectHolderList.size(); i++) {
                multiSelectHolderList.get(i).isSelect = false;
            }
            multiSelectHolderList.clear();
        }
        if(null != holderList){
            for(int i = 0; i < holderList.size(); i++) {
                holderList.get(i).isSelect = false;
            }
            holderList.clear();
        }
    }
}
