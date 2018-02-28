package com.example.qinshi.voicedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by qinshi on 2/27/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CustomDataEntity> dataList;
    private Map<String, ResultEntity> resultMap;

    public CustomAdapter(Context context, List<CustomDataEntity> dataList,
                         Map<String, ResultEntity> resultMap) {
        this.context = context;
        this.dataList = dataList;
        this.resultMap = resultMap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomInputView view = new CustomInputView(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomDataEntity entity = dataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemView.setKeyText(entity.getName());
        if(null != resultMap && resultMap.size() > 0 && null != resultMap.get(entity.getKey())) {
            entity.setValue(resultMap.get(entity.getKey()).getValue());
        }
        if(TextUtils.isEmpty(entity.getValue())){
            viewHolder.itemView.setValue("");
            viewHolder.itemView.setHintValue(entity.getValueHint());
        }else {
            viewHolder.itemView.setValue(entity.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public CustomInputView itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = (CustomInputView) itemView;
        }
    }
}
