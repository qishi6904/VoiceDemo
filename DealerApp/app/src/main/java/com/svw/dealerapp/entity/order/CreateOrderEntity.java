package com.svw.dealerapp.entity.order;

import com.svw.dealerapp.entity.ReqEntity;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;

import java.util.List;

/**
 * 创建订单入参Entity
 * Created by xupan on 28/08/2017.
 */

public class CreateOrderEntity extends ReqEntity {

    private String orderId;//此字段给编辑订单使用
    private String oppId;
    private String orgId;
    private String provinceId;
    private String cityId;
    private String srcTypeId;
    private String channelId;

    private String custName;
    private String custGender;
    private String custMobile;
    private String custAddress;
    private String custEmail;
    private String certType;
    private String certNum;
    private String custType;
    private String custIndustry;
    private String custDuty;
    private String corpNature;

    private String seriesId;//车系
    private String modelId;//车型
    private String outsideColorId;
    private String insideColorId;
    private String optionPackage;
    private String paymentMoney;//定金金额
    private String currentMoney;//实际成交价
    private String deliveryDate;

    private String carOwnerName;//置换姓名
    private String carOwnerTel;//置换电话
    private String carOwnerVin;//车主VIN
    private String carOwnerCard;//车主会员卡号

    private String recomName; //推荐人姓名
    private String recomMobile;//推荐人手机
    private String recomVin;//推荐人VIN
    private String recomCard;//推荐人会员卡号
    private String carServiceCode;//售后代码
    private String salesConsultant;

    private List<OptionalPackageEntity.OptionListBean> ecCarOptions;//选装包

    public String getOptionPackage() {
        return optionPackage;
    }

    public void setOptionPackage(String optionPackage) {
        this.optionPackage = optionPackage;
    }

    public String getOppId() {
        return oppId;
    }

    public void setOppId(String oppId) {
        this.oppId = oppId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSrcTypeId() {
        return srcTypeId;
    }

    public void setSrcTypeId(String srcTypeId) {
        this.srcTypeId = srcTypeId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustGender() {
        return custGender;
    }

    public void setCustGender(String custGender) {
        this.custGender = custGender;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustDuty() {
        return custDuty;
    }

    public void setCustDuty(String custDuty) {
        this.custDuty = custDuty;
    }

    public String getCorpNature() {
        return corpNature;
    }

    public void setCorpNature(String corpNature) {
        this.corpNature = corpNature;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getOutsideColorId() {
        return outsideColorId;
    }

    public void setOutsideColorId(String outsideColorId) {
        this.outsideColorId = outsideColorId;
    }

    public String getInsideColorId() {
        return insideColorId;
    }

    public void setInsideColorId(String insideColorId) {
        this.insideColorId = insideColorId;
    }

    public String getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(String paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(String currentMoney) {
        this.currentMoney = currentMoney;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getCarOwnerVin() {
        return carOwnerVin;
    }

    public void setCarOwnerVin(String carOwnerVin) {
        this.carOwnerVin = carOwnerVin;
    }

    public String getCarOwnerCard() {
        return carOwnerCard;
    }

    public void setCarOwnerCard(String carOwnerCard) {
        this.carOwnerCard = carOwnerCard;
    }

    public String getCarServiceCode() {
        return carServiceCode;
    }

    public void setCarServiceCode(String carServiceCode) {
        this.carServiceCode = carServiceCode;
    }

    public String getCarOwnerTel() {
        return carOwnerTel;
    }

    public void setCarOwnerTel(String carOwnerTel) {
        this.carOwnerTel = carOwnerTel;
    }

    public String getRecomName() {
        return recomName;
    }

    public void setRecomName(String recomName) {
        this.recomName = recomName;
    }

    public String getRecomMobile() {
        return recomMobile;
    }

    public void setRecomMobile(String recomMobile) {
        this.recomMobile = recomMobile;
    }

    public String getRecomVin() {
        return recomVin;
    }

    public void setRecomVin(String recomVin) {
        this.recomVin = recomVin;
    }

    public String getRecomCard() {
        return recomCard;
    }

    public void setRecomCard(String recomCard) {
        this.recomCard = recomCard;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<OptionalPackageEntity.OptionListBean> getEcCarOptions() {
        return ecCarOptions;
    }

    public void setEcCarOptions(List<OptionalPackageEntity.OptionListBean> ecCarOptions) {
        this.ecCarOptions = ecCarOptions;
    }

//    public static class OptionListBean {
//        /**
//         * optionCode : PTG
//         * optionNameEn :
//         * optionNameCn : 舒雅选装包，标配7座
//         * list : [{"optionCode":"PT8","optionNameEn":"","optionNameCn":"集成式儿童座椅"},{"optionCode":"PTI","optionNameEn":"","optionNameCn":"舒雅6座选装包"}]
//         */
//
//        private String optionCode;
//        private String optionNameEn;
//        private String optionNameCn;
//        private List<ListBean> list;
//
//        public String getOptionCode() {
//            return optionCode;
//        }
//
//        public void setOptionCode(String optionCode) {
//            this.optionCode = optionCode;
//        }
//
//        public String getOptionNameEn() {
//            return optionNameEn;
//        }
//
//        public void setOptionNameEn(String optionNameEn) {
//            this.optionNameEn = optionNameEn;
//        }
//
//        public String getOptionNameCn() {
//            return optionNameCn;
//        }
//
//        public void setOptionNameCn(String optionNameCn) {
//            this.optionNameCn = optionNameCn;
//        }
//
//        public List<ListBean> getList() {
//            return list;
//        }
//
//        public void setList(List<ListBean> list) {
//            this.list = list;
//        }
//
//        public static class ListBean {
//            /**
//             * optionCode : PT8
//             * optionNameEn :
//             * optionNameCn : 集成式儿童座椅
//             */
//
//            private String optionCode;
//            private String optionNameEn;
//            private String optionNameCn;
//
//            public String getOptionCode() {
//                return optionCode;
//            }
//
//            public void setOptionCode(String optionCode) {
//                this.optionCode = optionCode;
//            }
//
//            public String getOptionNameEn() {
//                return optionNameEn;
//            }
//
//            public void setOptionNameEn(String optionNameEn) {
//                this.optionNameEn = optionNameEn;
//            }
//
//            public String getOptionNameCn() {
//                return optionNameCn;
//            }
//
//            public void setOptionNameCn(String optionNameCn) {
//                this.optionNameCn = optionNameCn;
//            }
//
//            @Override
//            public String toString() {
//                return "ListBean{" +
//                        "optionCode='" + optionCode + '\'' +
//                        ", optionNameEn='" + optionNameEn + '\'' +
//                        ", optionNameCn='" + optionNameCn + '\'' +
//                        '}';
//            }
//        }
//
//        @Override
//        public String toString() {
//            return "OptionListBean{" +
//                    "optionCode='" + optionCode + '\'' +
//                    ", optionNameEn='" + optionNameEn + '\'' +
//                    ", optionNameCn='" + optionNameCn + '\'' +
//                    ", list=" + list +
//                    '}';
//        }
//    }

    @Override
    public String toString() {
        return "CreateOrderEntity{" +
                "oppId='" + oppId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", srcTypeId='" + srcTypeId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", custName='" + custName + '\'' +
                ", custGender='" + custGender + '\'' +
                ", custMobile='" + custMobile + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custEmail='" + custEmail + '\'' +
                ", certType='" + certType + '\'' +
                ", certNum='" + certNum + '\'' +
                ", custType='" + custType + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custDuty='" + custDuty + '\'' +
                ", corpNature='" + corpNature + '\'' +
                ", seriesId='" + seriesId + '\'' +
                ", modelId='" + modelId + '\'' +
                ", outsideColorId='" + outsideColorId + '\'' +
                ", insideColorId='" + insideColorId + '\'' +
                ", optionPackage='" + optionPackage + '\'' +
                ", paymentMoney='" + paymentMoney + '\'' +
                ", currentMoney='" + currentMoney + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", carOwnerName='" + carOwnerName + '\'' +
                ", carOwnerTel='" + carOwnerTel + '\'' +
                ", carOwnerVin='" + carOwnerVin + '\'' +
                ", carOwnerCard='" + carOwnerCard + '\'' +
                ", recomName='" + recomName + '\'' +
                ", recomMobile='" + recomMobile + '\'' +
                ", recomVin='" + recomVin + '\'' +
                ", recomCard='" + recomCard + '\'' +
                ", carServiceCode='" + carServiceCode + '\'' +
                ", salesConsultant='" + salesConsultant + '\'' +
                ", ecCarOptions=" + ecCarOptions +
                '}';
    }
}
