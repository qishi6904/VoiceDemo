package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svw.dealerapp.R;

import java.util.List;

/**
 * 选装包输入栏
 * Created by xupan on 30/11/2017.
 */

@Deprecated
public class CustomItemViewForOptionalPackage extends CustomItemViewBase<List<String>, List<String>> {

    private static final String TAG = "CustomItemViewForEditText";
    private Context mContext;
    private RecyclerView mRecyclerView;

    public CustomItemViewForOptionalPackage(Context context) {
        super(context);
        mContext = context;
    }

    public CustomItemViewForOptionalPackage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mRecyclerView = (RecyclerView) findViewById(R.id.custom_item_optional_package_rv);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_item_optional_package;
    }

    @Override
    public List<String> getInputData() {
        return null;
    }

    @Override
    public void setData(List<String> data) {
        if (data == null || data.isEmpty()) {
            mContentTextView.setText("");
            return;
        }
        if (mEnabled) {
            mContentTextView.setText(data.get(0));//可编辑模式永远只显示一行
        } else {
            ContentsAdapter adapter = new ContentsAdapter(data);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setAdapter(adapter);
        }
    }

    /**
     * 是否启用
     *
     * @param enabled true为编辑模式,false为查看模式
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEnabled = enabled;
        mContentTextView.setVisibility(enabled ? VISIBLE : GONE);
        mRecyclerView.setVisibility(enabled ? GONE : VISIBLE);
    }

    private class ContentsAdapter extends RecyclerView.Adapter<ContentsAdapter.RecyclerViewHolder> {

        private List<String> mList;

        ContentsAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_item_optional_package, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            String text = mList.get(position);
            holder.mContentTv.setText(text);
            holder.mIndexTv.setText(String.format("%s.", (position + 1)));
            holder.mGapView.setVisibility(position == 0 ? GONE : VISIBLE);//第一行不显示上边距
            holder.mDividingLine.setVisibility(position + 1 == mList.size() ? GONE : VISIBLE);//最后一行不显示分隔线
        }

        @Override
        public int getItemCount() {
            if (mList != null && !mList.isEmpty()) {
                return mList.size();
            } else {
                return 0;
            }
        }

        class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView mIndexTv, mContentTv;
            View mDividingLine, mGapView;

            RecyclerViewHolder(View itemView) {
                super(itemView);
                mIndexTv = (TextView) itemView.findViewById(R.id.item_custom_item_optional_package_index_tv);
                mContentTv = (TextView) itemView.findViewById(R.id.item_custom_item_optional_package_content_tv);
                mDividingLine = itemView.findViewById(R.id.item_custom_item_optional_package_dividing_line);
                mGapView = itemView.findViewById(R.id.item_custom_item_optional_package_gap_view);
            }
        }
    }
}
