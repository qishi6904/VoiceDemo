package com.svw.dealerapp.ui.newcustomer.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.svw.dealerapp.R;

/**
 * 创建新客户对话框页面
 * Created by xupan on 2017/4/28.
 */

public class MainNewCustomerDialogFragment extends DialogFragment {

    private Button mConfirmBt;
    private ImageButton mCloseBt;
    private Spinner mSourceSpinner, mActivitySpinner, mIntentionSpinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_main_create_customer, null);
        builder.setView(view);
        initView(view);
        setListeners();
        return builder.create();
    }

    private void initView(View view) {
        mConfirmBt = (Button) view.findViewById(R.id.dialog_confirm_bt);
        mCloseBt = (ImageButton) view.findViewById(R.id.dialog_close_ib);
        mSourceSpinner = (Spinner) view.findViewById(R.id.dialog_source_spinner);
        mActivitySpinner = (Spinner) view.findViewById(R.id.dialog_activity_spinner);
        mIntentionSpinner = (Spinner) view.findViewById(R.id.dialog_intention_spinner);
        initSpinners();
    }

    private void initSpinners() {
        String[] sourceString = new String[]{"A", "B", "C"};
        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sourceString);
        mSourceSpinner.setAdapter(sourceAdapter);
        ArrayAdapter<String> activityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sourceString);
        mActivitySpinner.setAdapter(activityAdapter);
        ArrayAdapter<String> intentionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sourceString);
        mIntentionSpinner.setAdapter(intentionAdapter);
    }

    private void setListeners() {
        mConfirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}