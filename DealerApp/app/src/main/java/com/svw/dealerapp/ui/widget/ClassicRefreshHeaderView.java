package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.irecyclerview.RefreshTrigger;
import com.svw.dealerapp.R;


/**
 * Created by aspsine on 16/3/14.
 */
public class ClassicRefreshHeaderView extends RelativeLayout implements RefreshTrigger {

    private TextView tvRefresh;

    private ProgressBar progressBar;

    private boolean rotated = false;

    private int mHeight;

    public ClassicRefreshHeaderView(Context context) {
        this(context, null);
    }

    public ClassicRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.layout_irecyclerview_classic_refresh_header_view, this);

        tvRefresh = (TextView) findViewById(R.id.tvRefresh);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {
        this.mHeight = headerHeight;
        progressBar.setIndeterminate(false);
    }

    @Override
    public void onMove(boolean isComplete, boolean automatic, int moved) {
        if (!isComplete) {
            progressBar.setVisibility(GONE);
            if (moved <= mHeight) {
                if (rotated) {
                    rotated = false;
                }
                tvRefresh.setText(getResources().getString(R.string.pull_down_to_refresh));
            } else {
                tvRefresh.setText(getResources().getString(R.string.release_to_refresh));
                if (!rotated) {
                    rotated = true;
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        progressBar.setVisibility(VISIBLE);
        tvRefresh.setVisibility(GONE);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        rotated = false;
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onReset() {
        rotated = false;
        progressBar.setVisibility(GONE);
        tvRefresh.setVisibility(VISIBLE);
        tvRefresh.setText(getResources().getString(R.string.pull_down_to_refresh));
    }
}
