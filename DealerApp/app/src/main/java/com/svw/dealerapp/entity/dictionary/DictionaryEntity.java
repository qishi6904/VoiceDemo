package com.svw.dealerapp.entity.dictionary;

import com.svw.dealerapp.entity.ResEntity;

import java.util.List;

/**
 * Created by lijinkui on 2017/5/15.
 */

public class DictionaryEntity extends ResEntity {

    /**
     * dictRelationList : [{"dictTypeId":"110","dictTypeName":"来源类型","dictRelation":[{"relaName":"朋友推荐","relaId":"10120","dictName":"电商","dictId":"11010"},{"relaName":"天猫","relaId":"10130","dictName":"线索","dictId":"11020"},{"relaName":"腾讯","relaId":"10140","dictName":"线索","dictId":"11020"},{"relaName":"微商城","relaId":"10150","dictName":"线索","dictId":"11020"},{"relaName":"易车","relaId":"10180","dictName":"线索","dictId":"11020"},{"relaName":"车享","relaId":"10190","dictName":"线索","dictId":"11020"},{"relaName":"EC","relaId":"10220","dictName":"线索","dictId":"11020"},{"relaName":"总部分配","relaId":"10100","dictName":"到店","dictId":"11030"},{"relaName":"汽车之家","relaId":"10160","dictName":"到店","dictId":"11030"},{"relaName":"太平洋","relaId":"10170","dictName":"到店","dictId":"11030"},{"relaName":"易车","relaId":"10180","dictName":"到店","dictId":"11030"},{"relaName":"爱卡","relaId":"10200","dictName":"到店","dictId":"11030"},{"relaName":"EC","relaId":"10220","dictName":"到店","dictId":"11030"},{"relaName":"汽车之家","relaId":"10160","dictName":"来电","dictId":"11040"},{"relaName":"太平洋","relaId":"10170","dictName":"来电","dictId":"11040"},{"relaName":"易车","relaId":"10180","dictName":"来电","dictId":"11040"},{"relaName":"爱卡","relaId":"10200","dictName":"来电","dictId":"11040"},{"relaName":"EC","relaId":"10220","dictName":"来电","dictId":"11040"}]},{"dictTypeId":"150","dictTypeName":"跟进方式","dictRelation":[{"relaName":"看车","relaId":"15510","dictName":"展厅","dictId":"15010"},{"relaName":"试乘试驾","relaId":"15530","dictName":"展厅","dictId":"15010"},{"relaName":"订单","relaId":"15540","dictName":"展厅","dictId":"15010"},{"relaName":"成功销售","relaId":"15550","dictName":"展厅","dictId":"15010"},{"relaName":"交车","relaId":"15560","dictName":"展厅","dictId":"15010"},{"relaName":"战败","relaId":"15570","dictName":"展厅","dictId":"15010"},{"relaName":"休眠","relaId":"15590","dictName":"展厅","dictId":"15010"},{"relaName":"其他","relaId":"15599","dictName":"展厅","dictId":"15010"},{"relaName":"继续跟进","relaId":"15520","dictName":"电话","dictId":"15020"},{"relaName":"战败","relaId":"15570","dictName":"电话","dictId":"15020"},{"relaName":"休眠","relaId":"15590","dictName":"电话","dictId":"15020"},{"relaName":"继续跟进","relaId":"15520","dictName":"短信","dictId":"15040"},{"relaName":"战败","relaId":"15570","dictName":"短信","dictId":"15040"},{"relaName":"休眠","relaId":"15590","dictName":"短信","dictId":"15040"},{"relaName":"继续跟进","relaId":"15520","dictName":"微信","dictId":"15030"},{"relaName":"战败","relaId":"15570","dictName":"微信","dictId":"15030"},{"relaName":"休眠","relaId":"15590","dictName":"微信","dictId":"15030"}]}]
     * version : 1.0
     * dictTypeIdList : [{"dictTypeId":"170","dictData":[{"dictName":"邀约到店","dictId":"17010"},{"dictName":"试乘试驾","dictId":"17020"},{"dictName":"订单","dictId":"17030"},{"dictName":"交车","dictId":"17040"}],"dictTypeName":"预约类型"},{"dictTypeId":"150","dictData":[{"dictName":"展厅","dictId":"15010"},{"dictName":"电话","dictId":"15020"},{"dictName":"微信","dictId":"15030"},{"dictName":"短信","dictId":"15040"}],"dictTypeName":"跟进方式"},{"dictTypeId":"140","dictData":[{"dictName":"保险","dictId":"14010"},{"dictName":"延保","dictId":"14020"},{"dictName":"上牌","dictId":"14030"},{"dictName":"其它","dictId":"14099"}],"dictTypeName":"其它需求类型"},{"dictTypeId":"130","dictData":[{"dictName":"私人","dictId":"13010"},{"dictName":"公司","dictId":"13020"},{"dictName":"政府","dictId":"13030"},{"dictName":"其它","dictId":"13099"}],"dictTypeName":"购买区分"},{"dictTypeId":"120","dictData":[{"dictName":"H","dictId":"12010"},{"dictName":"A","dictId":"12020"},{"dictName":"B","dictId":"12030"},{"dictName":"C","dictId":"12040"},{"dictName":"N","dictId":"12050"}],"dictTypeName":"潜客级别"},{"dictTypeId":"175","dictData":[{"dictName":"未完成","dictId":"17510"},{"dictName":"已完成","dictId":"17520"},{"dictName":"已逾期","dictId":"17530"},{"dictName":"取消","dictId":"17540"}],"dictTypeName":"预约状态"},{"dictTypeId":"110","dictData":[{"dictName":"电商","dictId":"11010"},{"dictName":"线索","dictId":"11020"},{"dictName":"到店","dictId":"11030"},{"dictName":"来电","dictId":"11040"},{"dictName":"外拓","dictId":"11050"}],"dictTypeName":"来源类型"},{"dictTypeId":"100","dictData":[{"dictName":"25以下","dictId":"10010"},{"dictName":"25-35","dictId":"10020"},{"dictName":"35-45","dictId":"10030"},{"dictName":"45-55","dictId":"10040"},{"dictName":"55以上","dictId":"10050"}],"dictTypeName":"年龄"},{"dictTypeId":"111","dictData":[{"dictName":"询价","dictId":"11110"},{"dictName":"试驾","dictId":"11120"},{"dictName":"订单","dictId":"11130"},{"dictName":"400电话","dictId":"11140"}],"dictTypeName":"来源种类"},{"dictTypeId":"155","dictData":[{"dictName":"看车","dictId":"15510"},{"dictName":"继续跟进","dictId":"15520"},{"dictName":"试乘试驾","dictId":"15530"},{"dictName":"订单","dictId":"15540"},{"dictName":"成功销售","dictId":"15550"},{"dictName":"交车","dictId":"15560"},{"dictName":"战败","dictId":"15570"},{"dictName":"休眠","dictId":"15590"},{"dictName":"其他","dictId":"15599"}],"dictTypeName":"跟进结果"},{"dictTypeId":"145","dictData":[{"dictName":"报纸/杂志","dictId":"14510"},{"dictName":"广播/电视","dictId":"14520"},{"dictName":"户外广告","dictId":"14530"},{"dictName":"移动互联","dictId":"14540"},{"dictName":"朋友推荐","dictId":"14550"},{"dictName":"外展/车展","dictId":"14560"},{"dictName":"其它","dictId":"14599"}],"dictTypeName":"信息途径"},{"dictTypeId":"135","dictData":[{"dictName":"首次","dictId":"13510"},{"dictName":"增购","dictId":"13520"},{"dictName":"置换","dictId":"13530"},{"dictName":"其它","dictId":"13599"}],"dictTypeName":"购买性质"},{"dictTypeId":"125","dictData":[{"dictName":"全款","dictId":"12510"},{"dictName":"分期","dictId":"12520"}],"dictTypeName":"付款方式"},{"dictTypeId":"115","dictData":[{"dictName":"跟进","dictId":"11510"},{"dictName":"战胜","dictId":"11520"},{"dictName":"战败","dictId":"11530"},{"dictName":"休眠","dictId":"11540"},{"dictName":"其它","dictId":"11599"}],"dictTypeName":"潜客状态"},{"dictTypeId":"105","dictData":[{"dictName":"未处理","dictId":"10510"},{"dictName":"已建卡","dictId":"10520"},{"dictName":"无效","dictId":"10530"}],"dictTypeName":"线索状态"}]
     */

    private String version;
    private List<DictRelationListBean> dictRelationList;
    private List<DictTypeIdListBean> dictTypeIdList;
    private List<OrderDictTypeIdListBean> orderDictTypeIdList;
    private List<DicCarSeriesAndModelBean> dicCarSeriesAndModel;
    private List<OpportunityStateListBean> opportunityStateList;
    private List<CompetitorsSeriesListBean> competitorsSeriesList;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<DictRelationListBean> getDictRelationList() {
        return dictRelationList;
    }

    public void setDictRelationList(List<DictRelationListBean> dictRelationList) {
        this.dictRelationList = dictRelationList;
    }

    public List<DictTypeIdListBean> getDictTypeIdList() {
        return dictTypeIdList;
    }

    public void setDictTypeIdList(List<DictTypeIdListBean> dictTypeIdList) {
        this.dictTypeIdList = dictTypeIdList;
    }

    public List<OrderDictTypeIdListBean> getOrderDictTypeIdList() {
        return orderDictTypeIdList;
    }

    public void setOrderDictTypeIdList(List<OrderDictTypeIdListBean> orderDictTypeIdList) {
        this.orderDictTypeIdList = orderDictTypeIdList;
    }

    public List<DicCarSeriesAndModelBean> getDicCarSeriesAndModel() {
        return dicCarSeriesAndModel;
    }

    public void setDicCarSeriesAndModel(List<DicCarSeriesAndModelBean> dicCarSeriesAndModel) {
        this.dicCarSeriesAndModel = dicCarSeriesAndModel;
    }

    public List<OpportunityStateListBean> getOpportunityStateList() {
        return opportunityStateList;
    }

    public void setOpportunityStateList(List<OpportunityStateListBean> opportunityStateList) {
        this.opportunityStateList = opportunityStateList;
    }

    public List<CompetitorsSeriesListBean> getCompetitorsSeriesList() {
        return competitorsSeriesList;
    }

    public void setCompetitorsSeriesList(List<CompetitorsSeriesListBean> competitorsSeriesList) {
        this.competitorsSeriesList = competitorsSeriesList;
    }

    public static class DictRelationListBean {
        /**
         * dictTypeId : 110
         * dictTypeName : 来源类型
         * dictRelation : [{"relaName":"朋友推荐","relaId":"10120","dictName":"电商","dictId":"11010"},{"relaName":"天猫","relaId":"10130","dictName":"线索","dictId":"11020"},{"relaName":"腾讯","relaId":"10140","dictName":"线索","dictId":"11020"},{"relaName":"微商城","relaId":"10150","dictName":"线索","dictId":"11020"},{"relaName":"易车","relaId":"10180","dictName":"线索","dictId":"11020"},{"relaName":"车享","relaId":"10190","dictName":"线索","dictId":"11020"},{"relaName":"EC","relaId":"10220","dictName":"线索","dictId":"11020"},{"relaName":"总部分配","relaId":"10100","dictName":"到店","dictId":"11030"},{"relaName":"汽车之家","relaId":"10160","dictName":"到店","dictId":"11030"},{"relaName":"太平洋","relaId":"10170","dictName":"到店","dictId":"11030"},{"relaName":"易车","relaId":"10180","dictName":"到店","dictId":"11030"},{"relaName":"爱卡","relaId":"10200","dictName":"到店","dictId":"11030"},{"relaName":"EC","relaId":"10220","dictName":"到店","dictId":"11030"},{"relaName":"汽车之家","relaId":"10160","dictName":"来电","dictId":"11040"},{"relaName":"太平洋","relaId":"10170","dictName":"来电","dictId":"11040"},{"relaName":"易车","relaId":"10180","dictName":"来电","dictId":"11040"},{"relaName":"爱卡","relaId":"10200","dictName":"来电","dictId":"11040"},{"relaName":"EC","relaId":"10220","dictName":"来电","dictId":"11040"}]
         */

        private String relaTypeId;
        private String relaTypeName;
        private List<DictRelationBean> dictRelation;

        public String getRelaTypeId() {
            return relaTypeId;
        }

        public void setRelaTypeId(String relaTypeId) {
            this.relaTypeId = relaTypeId;
        }

        public String getRelaTypeName() {
            return relaTypeName;
        }

        public void setRelaTypeName(String relaTypeName) {
            this.relaTypeName = relaTypeName;
        }

        public List<DictRelationBean> getDictRelation() {
            return dictRelation;
        }

        public void setDictRelation(List<DictRelationBean> dictRelation) {
            this.dictRelation = dictRelation;
        }

        public static class DictRelationBean {
            /**
             * relaName : 朋友推荐
             * relaId : 10120
             * dictName : 电商
             * dictId : 11010
             */

            private String relaName;
            private String relaId;
            private String dictName;
            private String dictId;

            public String getRelaName() {
                return relaName;
            }

            public void setRelaName(String relaName) {
                this.relaName = relaName;
            }

            public String getRelaId() {
                return relaId;
            }

            public void setRelaId(String relaId) {
                this.relaId = relaId;
            }

            public String getDictName() {
                return dictName;
            }

            public void setDictName(String dictName) {
                this.dictName = dictName;
            }

            public String getDictId() {
                return dictId;
            }

            public void setDictId(String dictId) {
                this.dictId = dictId;
            }
        }
    }

    public static class DictTypeIdListBean {
        /**
         * dictTypeId : 170
         * dictData : [{"dictName":"邀约到店","dictId":"17010"},{"dictName":"试乘试驾","dictId":"17020"},{"dictName":"订单","dictId":"17030"},{"dictName":"交车","dictId":"17040"}]
         * dictTypeName : 预约类型
         */

        private String dictTypeId;
        private String dictTypeName;
        private List<DictDataBean> dictData;

        public String getDictTypeId() {
            return dictTypeId;
        }

        public void setDictTypeId(String dictTypeId) {
            this.dictTypeId = dictTypeId;
        }

        public String getDictTypeName() {
            return dictTypeName;
        }

        public void setDictTypeName(String dictTypeName) {
            this.dictTypeName = dictTypeName;
        }

        public List<DictDataBean> getDictData() {
            return dictData;
        }

        public void setDictData(List<DictDataBean> dictData) {
            this.dictData = dictData;
        }

        public static class DictDataBean {
            /**
             * dictName : 邀约到店
             * dictId : 17010
             */

            private String dictName;
            private String dictId;

            public String getDictName() {
                return dictName;
            }

            public void setDictName(String dictName) {
                this.dictName = dictName;
            }

            public String getDictId() {
                return dictId;
            }

            public void setDictId(String dictId) {
                this.dictId = dictId;
            }
        }
    }

    public static class OrderDictTypeIdListBean {
        /**
         * dictTypeId : 170
         * dictData : [{"dictName":"邀约到店","dictId":"17010"},{"dictName":"试乘试驾","dictId":"17020"},{"dictName":"订单","dictId":"17030"},{"dictName":"交车","dictId":"17040"}]
         * dictTypeName : 预约类型
         */

        private String dictTypeId;
        private String dictTypeName;
        private List<DictDataBean> dictData;

        public String getDictTypeId() {
            return dictTypeId;
        }

        public void setDictTypeId(String dictTypeId) {
            this.dictTypeId = dictTypeId;
        }

        public String getDictTypeName() {
            return dictTypeName;
        }

        public void setDictTypeName(String dictTypeName) {
            this.dictTypeName = dictTypeName;
        }

        public List<DictDataBean> getDictData() {
            return dictData;
        }

        public void setDictData(List<DictDataBean> dictData) {
            this.dictData = dictData;
        }

        public static class DictDataBean {
            /**
             * dictName : 邀约到店
             * dictId : 17010
             */

            private String dictName;
            private String dictId;

            public String getDictName() {
                return dictName;
            }

            public void setDictName(String dictName) {
                this.dictName = dictName;
            }

            public String getDictId() {
                return dictId;
            }

            public void setDictId(String dictId) {
                this.dictId = dictId;
            }
        }
    }

    public static class DicCarSeriesAndModelBean {
        private String seriesNameCn;
        private String seriesNameEn;
        private String seriesId;
        private List<CarModelInfoBean> carModelInfo;

        public String getSeriesNameCn() {
            return seriesNameCn;
        }

        public void setSeriesNameCn(String seriesNameCn) {
            this.seriesNameCn = seriesNameCn;
        }

        public String getSeriesNameEn() {
            return seriesNameEn;
        }

        public void setSeriesNameEn(String seriesNameEn) {
            this.seriesNameEn = seriesNameEn;
        }

        public String getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }

        public List<CarModelInfoBean> getCarModelInfo() {
            return carModelInfo;
        }

        public void setCarModelInfo(List<CarModelInfoBean> carModelInfo) {
            this.carModelInfo = carModelInfo;
        }

        public static class CarModelInfoBean {
            private String modelId;
            private String modelDescCn;
            private String modelDescEn;

            public String getModelId() {
                return modelId;
            }

            public void setModelId(String modelId) {
                this.modelId = modelId;
            }

            public String getModelDescCn() {
                return modelDescCn;
            }

            public void setModelDescCn(String modelDescCn) {
                this.modelDescCn = modelDescCn;
            }

            public String getModelDescEn() {
                return modelDescEn;
            }

            public void setModelDescEn(String modelDescEn) {
                this.modelDescEn = modelDescEn;
            }
        }
    }

    public static class OpportunityStateListBean {
        private String stateDictOld;
        private String contactMethodDict;
        private String resultDict;
        private String resultDictName;
        private String stateWeight;

        public String getStateDictOld() {
            return stateDictOld;
        }

        public void setStateDictOld(String stateDictOld) {
            this.stateDictOld = stateDictOld;
        }

        public String getContactMethodDict() {
            return contactMethodDict;
        }

        public void setContactMethodDict(String contactMethodDict) {
            this.contactMethodDict = contactMethodDict;
        }

        public String getResultDict() {
            return resultDict;
        }

        public void setResultDict(String resultDict) {
            this.resultDict = resultDict;
        }

        public String getResultDictName() {
            return resultDictName;
        }

        public void setResultDictName(String resultDictName) {
            this.resultDictName = resultDictName;
        }

        public String getStateWeight() {
            return stateWeight;
        }

        public void setStateWeight(String stateWeight) {
            this.stateWeight = stateWeight;
        }
    }


    public static class CompetitorsSeriesListBean {
        /**
         * seriesNameEN : TiguanL
         * seriesId : 0T14
         * competitorVOList : [{"competitorId":"11010","competitorName":"Envision","competitorStatus":"0","remark":""},{"competitorId":"11020","competitorName":"CR-V","competitorStatus":"0","remark":""},{"competitorId":"11030","competitorName":"X-Trail","competitorStatus":"0","remark":""}]
         */

        private String seriesNameEN;
        private String seriesId;
        private List<CompetitorVOListBean> competitorVOList;

        public String getSeriesNameEN() {
            return seriesNameEN;
        }

        public void setSeriesNameEN(String seriesNameEN) {
            this.seriesNameEN = seriesNameEN;
        }

        public String getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }

        public List<CompetitorVOListBean> getCompetitorVOList() {
            return competitorVOList;
        }

        public void setCompetitorVOList(List<CompetitorVOListBean> competitorVOList) {
            this.competitorVOList = competitorVOList;
        }

        public static class CompetitorVOListBean {
            /**
             * competitorId : 11010
             * competitorName : Envision
             * competitorStatus : 0
             * remark :
             */

            private String competitorId;
            private String competitorName;
            private String competitorStatus;
            private String remark;

            public String getCompetitorId() {
                return competitorId;
            }

            public void setCompetitorId(String competitorId) {
                this.competitorId = competitorId;
            }

            public String getCompetitorName() {
                return competitorName;
            }

            public void setCompetitorName(String competitorName) {
                this.competitorName = competitorName;
            }

            public String getCompetitorStatus() {
                return competitorStatus;
            }

            public void setCompetitorStatus(String competitorStatus) {
                this.competitorStatus = competitorStatus;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
