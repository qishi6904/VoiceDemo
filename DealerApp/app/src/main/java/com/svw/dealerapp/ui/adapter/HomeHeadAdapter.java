package com.svw.dealerapp.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.DrawerMenuEntity;

import java.util.ArrayList;

import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by lijinkui on 2017/4/28.
 */

public class HomeHeadAdapter extends BaseAdapter {

    private ArrayList<DrawerMenuEntity> mList;
    private AdapterView.OnItemClickListener mItemClickListener;
    private Context context;

    public HomeHeadAdapter(ArrayList<DrawerMenuEntity> list, Context context) {
        this.mList = list;
        this.context = context;
    }

    class ViewHolder {
        public TextView textView;
        public ImageView imageView;
//        public BGABadgeRelativeLayout bgaRl;
        public RelativeLayout llBadge;
    }

    @Override
    public int getCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_home_head_grid, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_home_head_item);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_home_head_item);
//            viewHolder.bgaRl = (BGABadgeRelativeLayout) convertView.findViewById(R.id.bgaRl_home_head_item);
            viewHolder.llBadge = (RelativeLayout) convertView.findViewById(R.id.ll_home_head_badge);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position).getTitle());
        viewHolder.imageView.setImageResource(mList.get(position).getIcon());

        QBadgeView qv = new QBadgeView(context);
        qv.bindTarget(viewHolder.llBadge);
        qv.setBadgeGravity(Gravity.END | Gravity.TOP);
        qv.setBadgeNumber(99);
//        viewHolder.bgaRl.showTextBadge("99");

        return convertView;
    }

}
