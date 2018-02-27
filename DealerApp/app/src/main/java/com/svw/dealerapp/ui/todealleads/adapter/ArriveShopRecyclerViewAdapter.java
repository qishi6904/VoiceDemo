package com.svw.dealerapp.ui.todealleads.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.todealleads.entity.ArriveCustomerInfoEntity;
import com.svw.dealerapp.util.ToastUtils;

import java.util.List;

/**
 * Created by qinshi on 4/14/2017.
 */
@Deprecated
public class ArriveShopRecyclerViewAdapter extends BaseRecyclerViewAdapter<ArriveCustomerInfoEntity> implements View.OnClickListener {

    public ArriveShopRecyclerViewAdapter(Context context, List<ArriveCustomerInfoEntity> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_leads_arrive_shop, null);
        ArriveShopRecyclerViewHolder holder = new ArriveShopRecyclerViewHolder(view);
        GenericDraweeHierarchy hierarchy = holder.sdvLeftIcon.getHierarchy();
        hierarchy.setPlaceholderImage(R.mipmap.ic_leads_arrive_shop_item_left);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ArriveShopRecyclerViewHolder arriveViewHolder = (ArriveShopRecyclerViewHolder) holder;
        //// TODO: 5/2/2017
//        if(position == dataList.size() - 1){
        if(position == 29){
            arriveViewHolder.vDividerDown.setVisibility(View.VISIBLE);
        }else {
            arriveViewHolder.vDividerDown.setVisibility(View.GONE);
        }

        arriveViewHolder.llFrontView.setOnClickListener(this);
        arriveViewHolder.llBackView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
//        return null == dataList ? 0 : dataList.size();
        // TODO: 4/14/2017
        return 30;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_front_view:
                ToastUtils.showToast("frontView click");
                break;
            case R.id.ll_back_view:
                ToastUtils.showToast("backView click");
                break;
        }
    }

    private class ArriveShopRecyclerViewHolder extends RecyclerView.ViewHolder{

        LinearLayout llFrontView;
        LinearLayout llBackView;
        TextView tvArriveTimes;
        SimpleDraweeView sdvLeftIcon;
        TextView tvCustomerCode;
        TextView tvArriveTime;
        View vDividerDown;

        public ArriveShopRecyclerViewHolder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View view) {
            llBackView = (LinearLayout) view.findViewById(R.id.ll_back_view);
            llFrontView = (LinearLayout) view.findViewById(R.id.ll_front_view);
            tvArriveTimes = (TextView) view.findViewById(R.id.tv_arrive_times);
            sdvLeftIcon = (SimpleDraweeView) view.findViewById(R.id.sdv_left_icon);
            tvCustomerCode = (TextView) view.findViewById(R.id.tv_customer_code);
            tvArriveTime = (TextView) view.findViewById(R.id.tv_arrive_time);
            vDividerDown = view.findViewById(R.id.v_divider_down);
        }
    }
}
