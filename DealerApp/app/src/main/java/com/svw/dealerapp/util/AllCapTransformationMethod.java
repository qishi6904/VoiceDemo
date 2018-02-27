package com.svw.dealerapp.util;

import android.text.method.ReplacementTransformationMethod;

/**
 * Created by qinshi on 5/19/2017.
 * 用于EditText小写转大写
 */

public class AllCapTransformationMethod extends ReplacementTransformationMethod {

    @Override
    protected char[] getOriginal() {
        char[] lowerCase = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
        return lowerCase;
    }

    @Override
    protected char[] getReplacement() {
        char[] upperCase = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
        return upperCase;
    }

}
