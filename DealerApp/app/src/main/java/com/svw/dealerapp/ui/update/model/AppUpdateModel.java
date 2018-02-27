package com.svw.dealerapp.ui.update.model;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.RequestBaseParamsConfig;
import com.svw.dealerapp.api.UpdateNetwork;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.update.AppUpdateEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;
import com.svw.dealerapp.ui.update.contract.AppUpdateContract;
import com.svw.dealerapp.util.ManifestUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by qinshi on 7/3/2017.
 */

public class AppUpdateModel implements AppUpdateContract.Model {
    @Override
    public Observable<AppUpdateEntity> checkAppUpdate() {
        Map<String, String> params = new HashMap<>();
        //fr.im上应用的api_token
        params.put("api_token", ManifestUtils.getMetaDataValue("check_update_token"));
        //fr.im上应用的type
        params.put("type", "android");
        return UpdateNetwork.getInstance().getAppUpdateApi()
                .checkAppUpdate(DealerApp.getContext().getPackageName(), params)
                .compose(RxSchedulers.<AppUpdateEntity>io_main());
    }

}
