package com.svw.dealerapp.ui.login;

import com.svw.dealerapp.api.NetworkManager;
import com.svw.dealerapp.api.login.LoginApi;
import com.svw.dealerapp.entity.ResEntity;
import com.svw.dealerapp.entity.login.LoginEntity;
import com.svw.dealerapp.mvpframe.rx.RxSchedulers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by lijinkui on 2017/5/9.
 */

@Deprecated
public class LoginModel implements LoginContract.Model{

    private Map<String, String> optionsForUserLogin(String userId, String password) {

        String serial = android.os.Build.SERIAL;
        if("".equals(serial)||"unknown".equals(serial)){
            serial = "1234567890";
        }
        String carrier= android.os.Build.MANUFACTURER;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String currentDate = simpleDateFormat.format(new Date());

        Map<String, String> options = new HashMap<String, String>();
        options.put("appId", "com.svw.dealerapp");
        options.put("appType", "101");
        options.put("seqNo", "ea185595-4b35-4bf1-b8da-2f6036b12667");
        options.put("reqTime", currentDate);

        options.put("userId", userId);
        options.put("userName", "");
        options.put("password", password);

        options.put("deviceType", carrier);
        options.put("deviceId", serial);
        options.put("ipAddress", "");
        options.put("userLocation", "");

        return options;
    }

    @Override
    public Observable<ResEntity<LoginEntity>> userLogin(String userId, String password) {
        return NetworkManager.getInstance().getApiInstance(LoginApi.class,false)
                .userLogin(optionsForUserLogin(userId, password))
                .compose(RxSchedulers.<ResEntity<LoginEntity>>io_main());
    }

//    @Override
//    public Observable<ResEntity<DictionaryEntity>> getDictionary(String version) {
//        return NetworkManager.getInstance().getDictionaryApi()
//                .getDictionary(optionsForDictionary(version))
//                .compose(RxSchedulers.<ResEntity<DictionaryEntity>>io_main());
//    }
//
//    private Map<String, String> optionsForDictionary(String version) {
//
//        Map<String, String> options = new HashMap<String, String>();
//        options.put("version", version);
//        options = RequestBaseParamsConfig.addRequestBaseParams(options);
//
//        return options;
//    }
}
