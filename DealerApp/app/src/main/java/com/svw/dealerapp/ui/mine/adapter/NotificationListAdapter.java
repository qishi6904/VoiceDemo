package com.svw.dealerapp.ui.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.entity.mine.NotificationEntity.NotificationInfoEntity;
import com.svw.dealerapp.ui.mine.fragment.NotificationListFragment;
import com.svw.dealerapp.util.DateUtils;

import java.util.List;

/**
 * Created by qinshi on 6/1/2017.
 */

public class NotificationListAdapter extends BaseRecyclerViewAdapter<NotificationInfoEntity> {

    private OnItemClickListener onItemClickListener;
    private NotificationListFragment fragment;

    public NotificationListAdapter(Context context, List<NotificationInfoEntity> dataList, NotificationListFragment fragment) {
        super(context, dataList);
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_notification, null);
        final Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Holder viewHolder = (Holder) holder;
        final NotificationInfoEntity entity = dataList.get(position);
        if(position == 0){
            viewHolder.vUpDivider.setVisibility(View.VISIBLE);
        }else {
            viewHolder.vUpDivider.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(entity.getNoticeTitle());
        viewHolder.tvContent.setText(entity.getNotifDesc());
        viewHolder.tvTime.setText(DateUtils.dateFormat("yyyy.MM.dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", entity.getNotifDate()));

        if(entity.isSelect()){
            viewHolder.ivSelectIndicator.setImageResource(R.mipmap.ic_notification_select);
        }else {
            viewHolder.ivSelectIndicator.setImageResource(R.mipmap.ic_notification_unselect);
        }

        if("1".equals(entity.getNotifStatus())){
            viewHolder.vReadIndicator.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.vReadIndicator.setVisibility(View.VISIBLE);
        }

//        if(entity.isShowSingleLine()){
//            viewHolder.tvContent.setSingleLine(true);
//        }else {
//            viewHolder.tvContent.setSingleLine(false);
//        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragment.isShowEdit()) {
                    if (entity.isSelect()) {
                        viewHolder.ivSelectIndicator.setImageResource(R.mipmap.ic_notification_unselect);
                    } else {
                        viewHolder.ivSelectIndicator.setImageResource(R.mipmap.ic_notification_select);
                    }
                    entity.setSelect(!entity.isSelect());
                }else {
//                    if(entity.isShowSingleLine()) {
//                        viewHolder.tvContent.setSingleLine(false);
//                    }else {
//                        viewHolder.tvContent.setSingleLine(true);
//                    }
//                    entity.setShowSingleLine(!entity.isShowSingleLine());
                    viewHolder.vReadIndicator.setVisibility(View.GONE);
                }
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(v, position, entity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends RecyclerView.ViewHolder{

        View vUpDivider;
        View vReadIndicator;
        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;
        View itemView;
        ImageView ivSelectIndicator;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            assignViews(itemView);
        }

        private void assignViews(View view) {
            vUpDivider = view.findViewById(R.id.v_up_divider);
            vReadIndicator = view.findViewById(R.id.v_read_indicator);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvContent = (TextView) view.findViewById(R.id.tv_content);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            ivSelectIndicator = (ImageView) view.findViewById(R.id.iv_select_indicator);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position, NotificationInfoEntity entity);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
}
