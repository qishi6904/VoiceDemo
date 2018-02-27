package com.svw.dealerapp.ui.todealleads.fragment;

import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.ui.adapter.BaseRecyclerViewAdapter;
import com.svw.dealerapp.ui.todealleads.adapter.ArriveShopRecyclerViewAdapter;
import com.svw.dealerapp.mvpframe.listfragment.BaseListFragment;

/**
 * Created by qinshi on 4/28/2017.
 * 到店Fragment
 */
@Deprecated
public class ArriveShopFragment extends BaseListFragment {

    @Override
    public BaseRecyclerViewAdapter getAdapter() {
        ArriveShopRecyclerViewAdapter adapter = new ArriveShopRecyclerViewAdapter(getActivity(), presenter.getDataList());
        return adapter;
    }
}
