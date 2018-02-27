package com.svw.dealerapp.ui.optionalpackage.presenter;

import android.text.TextUtils;

import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentContract;
import com.svw.dealerapp.mvpframe.listfragment.ListFragmentPresenter;
import com.svw.dealerapp.ui.optionalpackage.fragment.OptionalPackageListFragment;

import java.util.List;

/**
 * Created by qinshi on 12/1/2017.
 */

public class OptionalPackageListPresenter extends ListFragmentPresenter<OptionalPackageEntity, OptionalPackageEntity.OptionListBean> {

    public OptionalPackageListPresenter(ListFragmentContract.View<OptionalPackageEntity, OptionalPackageEntity.OptionListBean> view, ListFragmentContract.Model<ResEntity<OptionalPackageEntity>> model) {
        super(view, model);
    }

    @Override
    public void loadMore(OptionalPackageEntity optionalPackageEntity) {

    }

    @Override
    public void refresh(OptionalPackageEntity optionalPackageEntity) {
        List<OptionalPackageEntity.OptionListBean> dataList = optionalPackageEntity.getOptionList();
        if(null != dataList && dataList.size() > 0){
            this.getDataList().clear();
            this.getDataList().addAll(dataList);

            //设置上次选中的数据（包括父类和子类选装包）的isSelect属性为true
            OptionalPackageListFragment fragment = (OptionalPackageListFragment) mView;
            List<OptionalPackageEntity.OptionListBean> defaultSelectData = fragment.getDefaultSelectData();
            if(null != defaultSelectData){
                for (int i = 0; i < defaultSelectData.size(); i++) {
                    OptionalPackageEntity.OptionListBean selectEntity = defaultSelectData.get(i);
                    for(int j = 0; j < getDataList().size(); j++) {
                        OptionalPackageEntity.OptionListBean entity = getDataList().get(j);
                        if(!TextUtils.isEmpty(selectEntity.getOptionCode()) &&
                                selectEntity.getOptionCode().equals(entity.getOptionCode())){
                            entity.setSelect(true);
                            for(int k = 0; k < selectEntity.getList().size(); k++) {
                                OptionalPackageEntity.OptionListBean.ListBean selectBean = selectEntity.getList().get(k);
                                for(int m = 0; m < entity.getList().size(); m++) {
                                    OptionalPackageEntity.OptionListBean.ListBean bean1 = entity.getList().get(m);
                                    if(!TextUtils.isEmpty(selectBean.getOptionCode()) &&
                                            selectBean.getOptionCode().equals(bean1.getOptionCode())) {
                                        bean1.setSelect(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            mView.refresh();
        }else {
            switch (requestType){
                case REQUEST_BY_PULL_DOWN:
                    dealNoDataByPullDown();
                    break;
                case REQUEST_BY_INIT:
                    mView.showNoDataLayout();
                    break;
            }
        }
    }
}
