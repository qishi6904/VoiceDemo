package com.svw.dealerapp.util;

import java.util.List;

/**
 * Created by qinshi on 1/12/2018.
 */

public class CollectionUtils {

    /**
     * 根据元素获取下标
     * @param list
     * @param t
     * @param <T>
     * @return
     */
    public static <T> int getIndex(List<T> list, T t) {
        if(null != list){
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i) == t) {
                    return i;
                }
            }
        }
        return -1;
    }

}
