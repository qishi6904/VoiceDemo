package com.svw.dealerapp.entity.home;

import java.util.List;

/**
 * Created by lijinkui on 2017/9/19.
 */

public class ActivityEntity {
    private List<ActivityInfoBean> returnList;

    public List<ActivityInfoBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ActivityInfoBean> returnList) {
        this.returnList = returnList;
    }

    public static class ActivityInfoBean {
        String activityId;
        String activitySubject;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivitySubject() {
            return activitySubject;
        }

        public void setActivitySubject(String activitySubject) {
            this.activitySubject = activitySubject;
        }
    }
}
