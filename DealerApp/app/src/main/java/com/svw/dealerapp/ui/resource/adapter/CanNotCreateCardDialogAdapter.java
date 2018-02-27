package com.svw.dealerapp.ui.resource.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.CustomDialogAdapter;
import com.svw.dealerapp.ui.resource.entity.CannotCreateReasonItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 5/10/2017.
 */

public class CanNotCreateCardDialogAdapter implements CustomDialogAdapter {

    private RecyclerView recyclerView;

    private Context context;
    private List<CannotCreateReasonItemEntity> dataList = new ArrayList<>();
    private OnSelectListener selectListener;

    public CanNotCreateCardDialogAdapter(Context context){
        dataList.add(new CannotCreateReasonItemEntity(context.getResources().getString(R.string.can_not_create_no_info),
                context.getResources().getString(R.string.can_not_create_no_info_detail)));
        dataList.add(new CannotCreateReasonItemEntity(context.getResources().getString(R.string.can_not_create_after_sale),
                context.getResources().getString(R.string.can_not_create_after_sale_detail)));
        dataList.add(new CannotCreateReasonItemEntity(context.getResources().getString(R.string.can_not_create_no_buy),
                context.getResources().getString(R.string.can_not_create_no_buy_detail)));
    }

    @Override
    public View getDialogView(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.dialog_can_not_create_card, null);
        assignViews(view);
        initView();
        return view;
    }

    private void assignViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_traffic_cannot_create);
    }

    private void initView(){
        CanNotCreateRecyclerAdapter recyclerAdapter = new CanNotCreateRecyclerAdapter(context, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new CanNotCreateRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, CannotCreateReasonItemEntity entity) {
                if(null != selectListener){
                    selectListener.onSelect(entity);
                }
            }
        });
    }

    public interface OnSelectListener{
        void onSelect(CannotCreateReasonItemEntity entity);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener){
        this.selectListener = onSelectListener;
    }
}
