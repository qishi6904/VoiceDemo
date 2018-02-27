package com.svw.dealerapp.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.svw.dealerapp.R;
import com.svw.dealerapp.entity.report.ReportHomeEntity;
import com.svw.dealerapp.mvpframe.BasePresenter;
import com.svw.dealerapp.ui.home.entity.AutoPlayEntity;
import com.svw.dealerapp.util.DensityUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinshi on 8/25/2017.
 */

public class AutoPlayLayout extends RelativeLayout {

    private final static int autoPlayFlag = 1001;

    private int delayTime = 3000;
    private ViewPager viewPager;
    private List<View> itemViewList;
    private Context context;
    private LinearLayout llIndicatorOuter;
    private View lastIndicator;
    private AutoPlayHandler autoPlayHandler;
    private List<ReportHomeEntity.ReportHomeItemEntity> data;

    public AutoPlayLayout(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public AutoPlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    public void init(Context context) {
        View rootView = View.inflate(context, R.layout.layout_auto_play, this);
        viewPager = (ViewPager) rootView.findViewById(R.id.vp_auto_play);
        llIndicatorOuter = (LinearLayout) rootView.findViewById(R.id.ll_indicator_outer);
    }

    public void setData(final List<ReportHomeEntity.ReportHomeItemEntity> data) {

        if(null == data || data.size() == 0){
            return;
        }

        this.data = data;

        autoPlayHandler = new AutoPlayHandler(this);
        viewPager.setOffscreenPageLimit(data.size() + 1);

        itemViewList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            itemViewList.add(createItemView(data.get(i)));
            llIndicatorOuter.addView(createIndicatorView());
        }
        itemViewList.add(0, createItemView(data.get(data.size() - 1)));
        itemViewList.add(createItemView(data.get(0)));

        llIndicatorOuter.getChildAt(0).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_auto_play_indicator_active_bg));
        lastIndicator = llIndicatorOuter.getChildAt(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int indicatorPosition;
                if (state == 0) {
                    if (viewPager.getCurrentItem() == data.size() + 1) {
                        viewPager.setCurrentItem(1, false);
                        indicatorPosition = 0;
                    } else if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(data.size(), false);
                        indicatorPosition = data.size() - 1;
                    }else {
                        indicatorPosition = viewPager.getCurrentItem() - 1;
                    }
                    if(lastIndicator != llIndicatorOuter.getChildAt(indicatorPosition)) {
                        llIndicatorOuter.getChildAt(indicatorPosition).setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_auto_play_indicator_active_bg));
                        lastIndicator.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_auto_play_indicator_bg));
                        lastIndicator = llIndicatorOuter.getChildAt(indicatorPosition);
                    }
                }
            }
        });

        viewPager.setAdapter(new AutoPlayAdapter(data));
        viewPager.setCurrentItem(1, false);

        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        autoPlayHandler.removeMessages(autoPlayFlag);
                        break;
                    case MotionEvent.ACTION_UP:
                        autoPlayHandler.sendEmptyMessageDelayed(autoPlayFlag, delayTime);
                        break;
                }
                return false;
            }
        });

        autoPlayHandler.sendEmptyMessageDelayed(autoPlayFlag, delayTime);

    }

    private View createIndicatorView(){
        View indicatorView = View.inflate(context, R.layout.ui_auto_play_indicator, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dp2px(context, 4), DensityUtil.dp2px(context, 4));
        layoutParams.leftMargin = DensityUtil.dp2px(context, 1.2f);
        layoutParams.rightMargin = DensityUtil.dp2px(context, 1.2f);
        indicatorView.setLayoutParams(layoutParams);
        return indicatorView;
    }

    private View createItemView(ReportHomeEntity.ReportHomeItemEntity entity){
//        View itemView = View.inflate(context, R.layout.item_home_auto_play, null);
//        TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
//        TextView tvNumber = (TextView) itemView.findViewById(R.id.tv_data_number);
//        RingChartView rcvChar = (RingChartView) itemView.findViewById(R.id.rcv_chart);
//
//        tvTitle.setText(entity.getTitle());
//        tvNumber.setText(entity.getNumber());
//        rcvChar.setData(entity.getRate());

        View itemView = View.inflate(context, R.layout.item_home_auto_play_new, null);
        TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        TextView tvNumber = (TextView) itemView.findViewById(R.id.tv_data_number);
        tvTitle.setText(entity.getName());
        tvNumber.setText(entity.getValue());

        return itemView;
    }

    private class AutoPlayAdapter extends PagerAdapter {

        private List<ReportHomeEntity.ReportHomeItemEntity> data;

        public AutoPlayAdapter(List<ReportHomeEntity.ReportHomeItemEntity> data){
            this.data = data;
        }

        @Override
        public int getCount() {
            return null == data ? 0 : data.size() + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(itemViewList.get(position));
            return itemViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class AutoPlayHandler extends Handler {

        WeakReference<AutoPlayLayout> weakReference;

        AutoPlayHandler(AutoPlayLayout weakReference) {
            this.weakReference = new WeakReference<>(weakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            int position = (viewPager.getCurrentItem() + 1) % (data.size() + 2);
            viewPager.setCurrentItem(position);
            autoPlayHandler.sendEmptyMessageDelayed(autoPlayFlag, delayTime);
        }
    }

    public void stopAutoPlay(){
        if(null != autoPlayHandler) {
            autoPlayHandler.removeMessages(autoPlayFlag);
        }
    }

    public void startAutoPlay(){
        if(null != autoPlayHandler) {
            autoPlayHandler.removeMessages(autoPlayFlag);
            autoPlayHandler.sendEmptyMessageDelayed(autoPlayFlag, delayTime);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                autoPlayHandler.removeMessages(autoPlayFlag);
                break;
            case MotionEvent.ACTION_UP:
                autoPlayHandler.sendEmptyMessageDelayed(autoPlayFlag, delayTime);
                break;
        }
        return true;
    }
}
