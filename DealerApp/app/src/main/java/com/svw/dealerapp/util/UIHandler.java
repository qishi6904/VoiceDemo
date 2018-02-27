package com.svw.dealerapp.util;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by lijinkui on 2018/1/26.
 */

public class UIHandler<T> extends Handler {

    protected WeakReference<T> ref;

    public UIHandler(T cls) {
        ref = new WeakReference<T>(cls);
    }

    public T getRef() {
        return ref != null ? ref.get() : null;
    }

}
