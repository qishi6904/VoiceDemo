package com.svw.dealerapp.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.svw.dealerapp.R;
import com.svw.dealerapp.ui.login.LoginActivity;

/**
 * Created by lijinkui on 2017/6/14.
 */

public class TokenInvalidReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context.getApplicationContext(), LoginActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(newIntent);
//        Toast.makeText(context, R.string.login_token_expired, Toast.LENGTH_LONG).show();
    }
}
