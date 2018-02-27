package com.svw.dealerapp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.adapter.BottomListDialogAdapter;

import java.util.List;

/**
 * Created by qinshi on 5/31/2017.
 */

public class BottomListDialog extends Dialog {

    private RecyclerView recyclerView;
    private RelativeLayout rlOuter;

    private BottomListDialogAdapter adapter;
    private List<String> dataList;
    private OnItemClickListener onItemClickListener;

    public BottomListDialog(@NonNull Context context, List<String> dataList) {
        super(context, R.style.custom_dialog);
        this.dataList = dataList;
        setContentView(R.layout.dialog_bottom_list);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        rlOuter = (RelativeLayout) findViewById(R.id.rl_outer);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new BottomListDialogAdapter(context, dataList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BottomListDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(null != onItemClickListener){
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });

        rlOuter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomListDialog.this.dismiss();
            }
        });
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

}
