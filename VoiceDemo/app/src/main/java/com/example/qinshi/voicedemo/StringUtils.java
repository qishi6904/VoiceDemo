package com.example.qinshi.voicedemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qinshi on 2/28/2018.
 */

public class StringUtils {

    public static List<String> getMatchStrings(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        List<String> stringList = new ArrayList<>();
        if(matcher.find()){
            for(int i=0; i<=matcher.groupCount(); i++){
                stringList.add(matcher.group(i));
                Log.e("ee", i+":"+matcher.group(i));
            }
        }
        return stringList;
    }

    public static String transformNumStrListToNumStr(List<String> numStrList, String content) {
        for(int i = 0; i < numStrList.size(); i++) {
            content = content.replaceAll(numStrList.get(i), transformStrToNumStr(numStrList.get(i)));
        }
        return content;
    }

    public static String transformStrToNumStr(String str) {
        str = str.replaceAll("点", ".");
        str = str.replaceAll("一", "1");
        str = str.replaceAll("二", "2");
        str = str.replaceAll("三", "3");
        str = str.replaceAll("四", "4");
        str = str.replaceAll("五", "5");
        str = str.replaceAll("六", "6");
        str = str.replaceAll("七", "7");
        str = str.replaceAll("八", "8");
        str = str.replaceAll("九", "9");
        str = str.replaceAll("零", "0");
        return str;
    }
}
