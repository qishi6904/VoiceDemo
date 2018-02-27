package com.svw.dealerapp.ui.splash;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.svw.dealerapp.R;
import com.svw.dealerapp.common.BaseFragment;

/**
 * Created by lijinkui on 2017/7/5.
 */

public class SplashSingleFragment extends BaseFragment {

    private SimpleDraweeView singleGuide;
    private String url;
    private Uri uri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        url = getArguments().getString("url");
        uri = Uri.parse(url);
        View view = View.inflate(getActivity(), R.layout.fragment_splash_guide, null);
        singleGuide = (SimpleDraweeView) view.findViewById(R.id.sdv_single_guide);
        singleGuide.setImageURI(uri);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
