package com.svw.dealerapp.ui.report.contract;

import com.svw.dealerapp.entity.report.ReportHomeEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by qinshi on 8/29/2017.
 */

public class ReportHomeContract {

    public interface View{
        void getTabListSuccess(List<ReportHomeEntity.ReportHomeItemEntity> tabList);

        void getTabListEmpty();
    }

    public interface Presenter{

        List<ReportHomeEntity.ReportHomeItemEntity> getTabList();

    }

    public interface Model{

    }

}
