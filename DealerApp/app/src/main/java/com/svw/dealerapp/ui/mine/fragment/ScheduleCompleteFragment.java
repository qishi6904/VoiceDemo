package com.svw.dealerapp.ui.mine.fragment;

import android.os.Bundle;

import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity;
import com.svw.dealerapp.entity.mine.ScheduleCompleteEntity.ScheduleCompleteInfoEntity;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.mine.adapter.ScheduleCompleteAdapter;
import com.svw.dealerapp.ui.mine.model.ScheduleCompleteModel;
import com.svw.dealerapp.ui.mine.presenter.ScheduleCompletePresenter;
import com.svw.dealerapp.util.JLog;
import com.svw.dealerapp.util.TalkingDataUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by qinshi on 6/1/2017.
 */

public class ScheduleCompleteFragment extends BaseListFragment<ScheduleCompleteEntity, ScheduleCompleteInfoEntity> {
    private ScheduleCompleteAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScheduleCompletePresenter(this, new ScheduleCompleteModel());
    }

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        adapter = new ScheduleCompleteAdapter(getActivity(), presenter.getDataList());
        return adapter;
    }

    @Override
    public String getFilter() {
        try {
            return URLEncoder.encode("{\"appmStatusId\": \"1\"}", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.getFilter();
    }

    @Override
    public void onShow() {
        super.onShow();
        TalkingDataUtils.onPageStart(getActivity(), "我的-预约-已结束");
        JLog.i("talkingDataFlag-show", "我的-预约-已结束");
    }

    @Override
    public void onHide() {
        super.onHide();
        TalkingDataUtils.onPageEnd(getActivity(), "我的-预约-已结束");
        JLog.i("talkingDataFlag-hide", "我的-预约-已结束");
    }
}
