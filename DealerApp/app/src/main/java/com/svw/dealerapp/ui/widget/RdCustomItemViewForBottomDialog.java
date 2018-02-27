package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.RdRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通用的输入栏，会显示底部弹出框，其中包括平铺的RecyclerView
 * Created by xupan on 24/01/2018.
 */

public class RdCustomItemViewForBottomDialog extends RdCustomItemViewBase<List<String>, List<String>> {

    private RdBottomInDialog mBottomSheetDialog;
    private RecyclerView mRecyclerView;
    private Button mSubmitBt;
    private ImageView mCloseIv;

    private RdRecyclerViewAdapter mAdapter;
    private List<String> mCurrentSelection = new ArrayList<>();

    public RdCustomItemViewForBottomDialog(Context context) {
        super(context);
    }

    public RdCustomItemViewForBottomDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
    protected int getLayoutId() {
        return R.layout.rd_custom_item_picker;
    }

    @Override
    public List<String> getInputData() {
        return mCurrentSelection;
    }

    @Override
    public void setData(List<String> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        mCurrentSelection.clear();
        mCurrentSelection.addAll(data);
        StringBuffer stringBuffer = new StringBuffer();
        Map<String, String> itemMap = mAdapter.getItemMap();
        for (int i = 0; i < data.size(); i++) {
            mAdapter.addCheckedCodeSet(data.get(i));
            String name = itemMap.get(data.get(i));
            if(!TextUtils.isEmpty(name)) {
                stringBuffer.append(name);
                if (i != data.size() - 1) {
                    stringBuffer.append(",");
                }
            }
        }
        setTextForContentView(stringBuffer.toString());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initViews() {
        super.initViews();
        View view = View.inflate(mContext, R.layout.rd_dialog_bottom_recyclerview, null);
        View rootView = view.findViewById(R.id.root_view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rd_dialog_bottom_recyclerview_rv);
        mSubmitBt = (Button) view.findViewById(R.id.rd_dialog_bottom_recyclerview_bt);
        mCloseIv = (ImageView) view.findViewById(R.id.iv_close);
        mBottomSheetDialog = new RdBottomInDialog(mContext);
        mBottomSheetDialog.setContentView(view);

        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mBottomSheetDialog) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        mSubmitBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null) {
                    mCurrentSelection.clear();
                    mCurrentSelection.addAll(mAdapter.getCheckedCodeList());

                    if(null != mOnDataChangedListener) {
                        mOnDataChangedListener.onDateChanged(mAdapter.getCheckedCodeList());
                    }

                    StringBuffer stringBuffer = new StringBuffer();
                    if(null != mAdapter.getCheckedNameList()) {
                        for (int i = 0; i < mAdapter.getCheckedNameList().size(); i++) {
                            stringBuffer.append(mAdapter.getCheckedNameList().get(i));
                            if(i != mAdapter.getCheckedNameList().size() - 1) {
                                stringBuffer.append("，");
                            }
                        }
                    }
                    setTextForContentView(stringBuffer.toString());
                }
                mBottomSheetDialog.dismiss();
            }
        });

        mCloseIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
    }

    @Override
    protected void onRootLayoutClicked() {
        super.onRootLayoutClicked();
        if (mEnabled && mClickable && mBottomSheetDialog != null) {
            mBottomSheetDialog.show();
        }
    }

    public void dismiss() {
        if(null != mBottomSheetDialog) {
            mBottomSheetDialog.dismiss();
        }
    }
}
