package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.RdRecyclerViewAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by xupan on 22/01/2018.
 */

public class RdCustomItemViewForRecycleView extends RdCustomItemViewBase<List<String>, List<String>> {

    private RecyclerView mRecyclerView;
    private RdRecyclerViewAdapter mAdapter;
    private Context mContext;

    public RdCustomItemViewForRecycleView(Context context) {
        super(context);
        mContext = context;
    }

    public RdCustomItemViewForRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rd_custom_item_recyclerview;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mRecyclerView = (RecyclerView) findViewById(R.id.rd_custom_item_recyclerview);
    }

    public void initAdapter(Map<String, String> map, int mode) {
        mAdapter = new RdRecyclerViewAdapter(map, mode);
        FlexboxLayoutManager layoutManager = initLayoutManager();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private FlexboxLayoutManager initLayoutManager() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexWrap(AlignContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        return layoutManager;
    }

    @Override
    public List<String> getInputData() {
        return mAdapter != null ? mAdapter.getCheckedCodeList() : null;
    }

    @Override
    public void setData(List<String> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        for (String item : data) {
            mAdapter.addCheckedCodeSet(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setOnDataChangedListener(OnDataChangedListener listener) {
        mAdapter.setOnDataChangedListener(listener);
    }
}
