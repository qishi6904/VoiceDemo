package com.svw.dealerapp.ui.newcustomer.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.newcustomer.RemarkEntity;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.util.DateUtils;

import java.util.List;

/**
 * Created by qinshi on 7/14/2017.
 */

public class RemarkListAdapter extends BaseRecyclerViewAdapter<RemarkEntity.RemarkEntityInfo> {

//    private List<RemarkEntity.RemarkEntityInfo> dataList;

    public RemarkListAdapter(Context context, List<RemarkEntity.RemarkEntityInfo> dataList) {
        super(context, dataList);
//        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_customer_detail_remark_list, null);
        Holder holder = new Holder(view);
        GenericDraweeHierarchy hierarchy = holder.sdvRemarkUserHeader.getHierarchy();
        hierarchy.setPlaceholderImage(R.mipmap.ic_kpi_default_sales);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder viewHolder = (Holder) holder;
        RemarkEntity.RemarkEntityInfo remarkEntityInfo = dataList.get(position);

        if(position == dataList.size() - 1){
            viewHolder.vBottomLine.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.vBottomLine.setVisibility(View.VISIBLE);
        }

        viewHolder.tvRemarkUserName.setText(remarkEntityInfo.getCreateUserName());
        viewHolder.tvRemarkContent.setText(remarkEntityInfo.getOppComment());
        viewHolder.tvRemarkTime.setText(DateUtils.dateFormat("yyyy-MM-dd HH:mm", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", remarkEntityInfo.getCreateTime()));

        Uri uri = Uri.parse(remarkEntityInfo.getCreateUserImage());
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(false)
                        .build();
        viewHolder.sdvRemarkUserHeader.setController(draweeController);

    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class Holder extends RecyclerView.ViewHolder {

        private SimpleDraweeView sdvRemarkUserHeader;
        private TextView tvRemarkUserName;
        private TextView tvRemarkContent;
        private TextView tvRemarkTime;
        private View vBottomLine;

        public Holder(View itemView) {
            super(itemView);
            assignViews(itemView);
        }

        private void assignViews(View itemView) {
            sdvRemarkUserHeader = (SimpleDraweeView) itemView.findViewById(R.id.sdv_remark_user_header);
            tvRemarkUserName = (TextView) itemView.findViewById(R.id.tv_remark_user_name);
            tvRemarkContent = (TextView) itemView.findViewById(R.id.tv_remark_content);
            tvRemarkTime = (TextView) itemView.findViewById(R.id.tv_remark_time);
            vBottomLine = itemView.findViewById(R.id.v_bottom_line);
        }
    }
}
