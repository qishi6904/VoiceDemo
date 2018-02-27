package com.svw.dealerapp.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;
import android.widget.TextView;

import com.svw.dealerapp.DealerApp;
import com.svw.dealerapp.entity.optionalpackage.OptionalPackageEntity;
import com.svw.dealerapp.ui.widget.CustomItemViewBase;
import com.svw.dealerapp.ui.widget.CustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.CustomItemViewForRadioButton;
import com.svw.dealerapp.ui.widget.RdCustomItemViewBase;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForOptionsPicker;
import com.svw.dealerapp.ui.widget.RdCustomItemViewForRadioButton;
import com.svw.dealerapp.util.dbtools.DBUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 公用Utils类
 * Created by xupan on 22/06/2017.
 */

public class CommonUtils {
    private static final String TAG = "CommonUtils";
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static void limitEditTextInput(final EditText editText, final TextView countdownTv, final int max) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                String result = String.format(Locale.CHINA, "%d/%d", length, max);
                countdownTv.setText(result);
            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    public static boolean isBeforeNow(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar givenCalendar = Calendar.getInstance();
        givenCalendar.setTime(date);
        Calendar now = Calendar.getInstance();
        return givenCalendar.before(now);
    }

    /**
     * Generate a value suitable for use in View#setId(int).
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    /**
     * 为CustomItemViewForOptionsPicker或CustomItemViewForRadioButton生成选项数据
     *
     * @param sourceMap 数据来源map
     * @param picker    PickerView
     */
    @Deprecated
    public static void initOptionsView(Map<String, String> sourceMap, CustomItemViewBase picker) {
        if (sourceMap == null || picker == null) {
            return;
        }
        Iterator<Map.Entry<String, String>> iterator = sourceMap.entrySet().iterator();
        List<String> codeList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            codeList.add(entry.getKey());
            valueList.add(entry.getValue());
        }
        //只有以下两种view能够调用initLists(或者说只有这两种view initList有意义)
        //所以initList()方法没有添加到CustomItemViewBase，无法使用多态，只能手动判断类型然后再转型
        if (picker instanceof CustomItemViewForOptionsPicker) {
            ((CustomItemViewForOptionsPicker) picker).initLists(codeList, valueList);
        } else if (picker instanceof CustomItemViewForRadioButton) {
            ((CustomItemViewForRadioButton) picker).initLists(codeList, valueList);
        }
    }

    /**
     * 为CustomItemViewForOptionsPicker或CustomItemViewForRadioButton生成选项数据
     *
     * @param sourceMap 数据来源map
     * @param picker    PickerView
     */
    public static void rdInitOptionsView(Map<String, String> sourceMap, RdCustomItemViewBase picker) {
        if (sourceMap == null || picker == null) {
            return;
        }
        Iterator<Map.Entry<String, String>> iterator = sourceMap.entrySet().iterator();
        List<String> codeList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            codeList.add(entry.getKey());
            valueList.add(entry.getValue());
        }
        //只有以下两种view能够调用initLists(或者说只有这两种view initList有意义)
        //所以initList()方法没有添加到CustomItemViewBase，无法使用多态，只能手动判断类型然后再转型
        if (picker instanceof RdCustomItemViewForOptionsPicker) {
            ((RdCustomItemViewForOptionsPicker) picker).initLists(codeList, valueList);
        } else if (picker instanceof RdCustomItemViewForRadioButton) {
            ((RdCustomItemViewForRadioButton) picker).initLists(codeList, valueList);
        }
    }

    /**
     * 生成最终的竞品车系选项map(先将map的key和value设置为一致，再添加“其他车系”)
     *
     * @param seriesId 当前车系id
     * @return 竞品车系选项map
     */
    public static Map<String, String> generateFinalCompetitorSeriesMap(String seriesId) {
        Map<String, String> map = DBUtils.getCompetitorsSeriesMap(seriesId);
        Map<String, String> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            resultMap.put(entry.getValue(), entry.getValue());//竞品车系最后传参为value，不传code
        }
        resultMap.put("其他车系", "其他车系");
        return resultMap;
    }

    /**
     * 跟进记录：15520|继续跟进 15530|试乘试驾 15540|订单
     * 潜客状态：11510|跟进 11512|试乘试驾 11514|订单
     * 潜客级别：12010|H 12020|A 12030|B 12040|C 12050|N
     *
     * @param first          是否首次跟进
     * @param level          潜客级别
     * @param statusOrRecord 首次跟进时，传入跟进记录; 非首次时，传入潜客状态
     * @return 延迟天数，依次为默认、开始、结束
     */
    public static int[] calculateDelayedDays(boolean first, String level, String statusOrRecord) {
        if (first) {
            //当跟进记录选择"继续跟进"或"试乘试驾"时，无论哪个级别都是1 0 1
            if ("15520".equals(statusOrRecord) || "15530".equals(statusOrRecord)) {
                return new int[]{1, 0, 1};
            } else if ("15540".equals(statusOrRecord)) {
                //订单
                return new int[]{3, 0, 365};
            } else {
                return new int[]{0, 0, 0};
            }
        } else {
            //潜客状态为“跟进”或“试乘试驾”
            if ("11510".equals(statusOrRecord) || "11512".equals(statusOrRecord)) {
                switch (level) {
                    case "12010"://H
                        return new int[]{3, 0, 3};
                    case "12020"://A
                        return new int[]{7, 0, 7};
                    case "12030"://B
                        return new int[]{15, 0, 15};
                    case "12040"://C
                        return new int[]{30, 0, 30};
                    case "12050"://N
                        return new int[]{30, 0, 365};
                    default:
                        return new int[]{0, 0, 0};
                }
            } else {
                return new int[]{3, 0, 365};
            }
        }
    }

    /**
     * 将后台返回的选装包数据转换成选装包控件能显示的数据格式
     *
     * @param list     后台返回的选装包数据
     * @param editable 是否可编辑（显示订单传false，其余全为true）
     * @return 选装包控件setData()的入参
     */
    public static List<String> getOptionalPackage(List<OptionalPackageEntity.OptionListBean> list, boolean editable) {
        List<String> resultList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return resultList;
        }
        if (editable) {
            String result = "";
            for (int i = 0; i < list.size(); i++) {
                OptionalPackageEntity.OptionListBean optionListBean = list.get(i);
                result += optionListBean.getOptionNameCn();
                if (i != list.size() - 1) {
                    result += "，";//每一个item用逗号隔开
                }
                List<OptionalPackageEntity.OptionListBean.ListBean> listBean = optionListBean.getList();
                if (listBean == null || listBean.isEmpty()) {
                    continue;
                }
                for (int j = 0; j < listBean.size(); j++) {
                    OptionalPackageEntity.OptionListBean.ListBean bean = listBean.get(j);
                    result += bean.getOptionNameCn();
                    if (j != listBean.size() - 1) {
                        result += "，";//每一个item用逗号隔开
                    }
                }
            }
            resultList.add(result);
        } else {
            for (OptionalPackageEntity.OptionListBean optionListBean : list) {
                String outerName = optionListBean.getOptionNameCn();
//                resultList.add(outerName);
                List<OptionalPackageEntity.OptionListBean.ListBean> listBean = optionListBean.getList();
                if (listBean == null || listBean.isEmpty()) {
                    resultList.add(outerName);
                    continue;
                }
//                for (OptionalPackageEntity.OptionListBean.ListBean bean : listBean) {
//                    String innerName = bean.getOptionNameCn();
//                    resultList.add(innerName);
//                }
                for (OptionalPackageEntity.OptionListBean.ListBean bean : listBean) {
                    outerName = outerName + "\n" + bean.getOptionNameCn();
                }
                resultList.add(outerName);
            }
        }
        return resultList;
    }

    /**
     * 将cookie同步到WebView
     *
     * @param url    WebView要加载的url
     * @param cookie 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public static boolean syncCookie(String url, String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(DealerApp.getContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
        String newCookie = cookieManager.getCookie(url);
        return TextUtils.isEmpty(newCookie) ? false : true;
    }

    /**
     * 判断是否有虚拟底部按钮
     *
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return hasNavigationBar;
    }

    /**
     * 获取底部虚拟按键高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

}
