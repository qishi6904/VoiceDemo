package com.svw.dealerapp.util;

import java.text.DecimalFormat;

/**
 * Created by qinshi on 7/4/2017.
 */

public class NumberUtils {

    /**
     * 格式化为1位小数
     * @param number
     * @return
     */
    public static String formatDecimalsOnePoint(double number){
        DecimalFormat decimalFormat = new DecimalFormat("##0.0");
        return decimalFormat.format(number);
    }
}
