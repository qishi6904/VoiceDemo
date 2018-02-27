package com.svw.dealerapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.widget.RdCustomItemViewBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 主要为RdCustomItemViewForRecycleView 和 RdCustomItemViewForBottomDialog 所使用的Adapter
 * Created by xupan on 24/01/2018.
 */

public class RdRecyclerViewAdapter extends RecyclerView.Adapter<RdRecyclerViewAdapter.ViewHolder> {

    public static final int MODE_SINGLE = 0;
    public static final int MODE_MULTIPLE = 1;
    public static final int MODE_OTHER = 2;

    private List<String> mCodeList = new ArrayList<>();
    private List<String> mValueList = new ArrayList<>();
    //        private Set<Integer> mCheckedIndexSet = new HashSet<>();
    private Set<String> mCheckedValueSet = new HashSet<>();
    private Set<String> mCheckedCodeSet = new HashSet<>();
    private int mCurrentMode = 2;
    private RdCustomItemViewBase.OnDataChangedListener mOnDataChangedListener;

    private Map<String, String> itemMap;

    public RdRecyclerViewAdapter(Map<String, String> map, int mode) {
        mCurrentMode = mode;
        this.itemMap = map;
        initLists(map);
    }

    public RdRecyclerViewAdapter(Map<String, String> map, int mode,
                                 RdCustomItemViewBase.OnDataChangedListener listener) {
        this(map, mode);
        mOnDataChangedListener = listener;
    }

    public void setOnDataChangedListener(RdCustomItemViewBase.OnDataChangedListener listener){
        mOnDataChangedListener = listener;
    }

    private void initLists(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        List<String> codeList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            codeList.add(entry.getKey());
            valueList.add(entry.getValue());
        }
        mCodeList.clear();
        mCodeList.addAll(codeList);
        mValueList.clear();
        mValueList.addAll(valueList);
    }

    public void addCheckedCodeSet(String code) {
        if (mCurrentMode == MODE_SINGLE) {
            mCheckedCodeSet.clear();
            mCheckedValueSet.clear();
            mCheckedCodeSet.add(code);
        } else if (mCurrentMode == MODE_MULTIPLE) {
            mCheckedCodeSet.add(code);
        } else if (mCurrentMode == MODE_OTHER) {

        }
    }

    public List<String> getCheckedCodeList() {
        List<String> list = new ArrayList<>();
        list.addAll(mCheckedCodeSet);
        return list;
    }

    public List<String> getCheckedNameList() {
        List<String> list = new ArrayList<>();
        if(null != itemMap) {
            Iterator<String> iterator = mCheckedCodeSet.iterator();
            while (iterator.hasNext()) {
                list.add(itemMap.get(iterator.next()));
            }
        }
        return list;
    }

    public Map<String, String> getItemMap(){
        return itemMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rd_custom_item_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        final String value = mValueList.get(adapterPosition);
        final String code = mCodeList.get(adapterPosition);
        holder.checkBox.setText(value);
        holder.checkBox.setChecked(mCheckedCodeSet.contains(code));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选中
                if (holder.checkBox.isChecked()) {
                    if (mCurrentMode == MODE_SINGLE) {
                        mCheckedCodeSet.clear();
                        mCheckedValueSet.clear();
                        mCheckedCodeSet.add(code);
                        mCheckedValueSet.add(value);
                    } else if (mCurrentMode == MODE_MULTIPLE) {
                        mCheckedCodeSet.add(code);
                        mCheckedValueSet.add(value);
                    } else if (mCurrentMode == MODE_OTHER) {

                    }
                } else {
                    if (mCurrentMode == MODE_SINGLE) {
                        mCheckedCodeSet.remove(code);
                        mCheckedValueSet.remove(value);
                    } else if (mCurrentMode == MODE_MULTIPLE) {
                        mCheckedCodeSet.remove(code);
                        mCheckedValueSet.remove(value);
                    } else if (mCurrentMode == MODE_OTHER) {

                    }
                }
                notifyDataSetChanged();
                if (mOnDataChangedListener != null) {
                    mOnDataChangedListener.onDateChanged(code);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCodeList == null) {
            return 0;
        } else {
            return mCodeList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.rd_item_new_customer_grid_checkbox);
        }

    }
}
