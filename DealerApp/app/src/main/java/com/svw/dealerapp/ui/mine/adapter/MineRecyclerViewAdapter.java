package com.svw.dealerapp.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.mine.entity.MineListItemEntity;
import com.svw.dealerapp.ui.widget.BottomListDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 5/31/2017.
 */

public class MineRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<MineListItemEntity> dataList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<Holder> holderList = new ArrayList<>();

    public MineRecyclerViewAdapter(Context context, List<MineListItemEntity> dataList){
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_mine_fragment, null);
        Holder holder = new Holder(view);
        holderList.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder viewHolder = (Holder) holder;
        MineListItemEntity entity = dataList.get(position);
        viewHolder.tvTitle.setText(entity.getTitle());
        viewHolder.ivIcon.setImageResource(entity.getIconId());
        viewHolder.tvNumber.setText(entity.getNumber());

        if(entity.isHasNew()){
            viewHolder.vHasNewIndicator.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vHasNewIndicator.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onItemClickListener){
                    onItemClickListener.onItemClick(viewHolder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        ImageView ivIcon;
        View vHasNewIndicator;
        TextView tvTitle;
        TextView tvNumber;
        View itemView;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_mine_item_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_mine_item_title);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_mine_item_number);
            vHasNewIndicator = itemView.findViewById(R.id.v_has_new_indicator);
        }

        public View getvHasNewIndicator(){
            return vHasNewIndicator;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public Holder getHolder(int index){
        if(index < holderList.size()){
            return holderList.get(index);
        }
        return null;
    }
}
