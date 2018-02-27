package com.svw.dealerapp.ui.entity;

/**
 * Created by qinshi on 4/27/2017.
 */

public class NavBarParamsEntity {

    private int tabs;                                //页签的数量
    private int showTabsOnePage;                     //单页显示的页签数量
    private String[] tabTitles;                      //页签标题
    private String[] tipTexts;                       //提示圈内容
    private String[] tabIconUrls;                    //页签图标url
    private int[] defaultIconIds;                    //默认页签图标id
    private int leftRightMargin;                      //页签左右缩进
    private int navBarHeight;                        //导航栏高度
    private int navPaddingBottom;                    //导航栏下内边距
    private int scrollLineHeight;                    //滑动下划线高度
    private int iconHeight;                          //页签图标高度
    private int iconWidth;                           //页签图标宽度
    private int titleTextSize;                       //页签标题文字大小
    private int titleTextColor;                      //页签标题文字颜色
    private int tipTextSize;                         //提示圈字体大小
    private int tipTextColor;                        //提示圈字体颜色
    private int tipRadius;                           //提示圈半径
    private int[] tipOffsetArr;                      //提示圈偏移
    private int scrollLineColor;                      //滑动下划线颜色
    private boolean isShowIcon = true;               //是否显示图标
    private boolean isShowTip = true;                //是否显示提示圈
    private boolean isShowScrollLine = true;          //是否显示下划线

    public NavBarParamsEntity(){

    }

    public NavBarParamsEntity(int tabs, int showTabsOnePage, String[] tabTitles, String[] tipTexts, String[] tabIconUrls,
                              int[] defaultIconIds, int leftRightMargin, int navBarHeight, int navPaddingBottom, int scrollLineHeight,
                              int iconHeight, int iconWidth, int titleTextSize, int titleTextColor,
                              int tipTextSize, int tipTextColor, int tipRadius, int[] tipOffsetArr, int scrollLineColor,
                              boolean isShowIcon, boolean isShowTip, boolean isShowScrollLine) {
        this.tabs = tabs;
        this.showTabsOnePage = showTabsOnePage;
        this.tabTitles = tabTitles;
        this.tipTexts = tipTexts;
        this.tabIconUrls = tabIconUrls;
        this.defaultIconIds = defaultIconIds;
        this.leftRightMargin = leftRightMargin;
        this.navBarHeight = navBarHeight;
        this.navPaddingBottom = navPaddingBottom;
        this.scrollLineHeight = scrollLineHeight;
        this.iconHeight = iconHeight;
        this.iconWidth = iconWidth;
        this.titleTextSize = titleTextSize;
        this.titleTextColor = titleTextColor;
        this.tipTextSize = tipTextSize;
        this.tipTextColor = tipTextColor;
        this.tipRadius = tipRadius;
        this.tipOffsetArr = tipOffsetArr;
        this.scrollLineColor = scrollLineColor;
        this.isShowIcon = isShowIcon;
        this.isShowTip = isShowTip;
        this.isShowScrollLine = isShowScrollLine;
    }

    public int getTabs() {
        return tabs;
    }

    public void setTabs(int tabs) {
        this.tabs = tabs;
    }

    public int getShowTabsOnePage() {
        return showTabsOnePage;
    }

    public void setShowTabsOnePage(int showTabsOnePage) {
        this.showTabsOnePage = showTabsOnePage;
    }

    public String[] getTabTitles() {
        return tabTitles;
    }

    public void setTabTitles(String[] tabTitles) {
        this.tabTitles = tabTitles;
    }

    public String[] getTipTexts() {
        return tipTexts;
    }

    public void setTipTexts(String[] tipTexts) {
        this.tipTexts = tipTexts;
    }

    public String[] getTabIconUrls() {
        return tabIconUrls;
    }

    public void setTabIconUrls(String[] tabIconUrls) {
        this.tabIconUrls = tabIconUrls;
    }

    public int[] getDefaultIconIds() {
        return defaultIconIds;
    }

    public void setDefaultIconIds(int[] defaultIconIds) {
        this.defaultIconIds = defaultIconIds;
    }

    public int getNavBarHeight() {
        return navBarHeight;
    }

    public void setNavBarHeight(int navBarHeight) {
        this.navBarHeight = navBarHeight;
    }

    public int getNavPaddingBottom() {
        return navPaddingBottom;
    }

    public void setNavPaddingBottom(int navPaddingBottom) {
        this.navPaddingBottom = navPaddingBottom;
    }

    public int getScrollLineHeight() {
        return scrollLineHeight;
    }

    public void setScrollLineHeight(int scrollLineHeight) {
        this.scrollLineHeight = scrollLineHeight;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public int getTipTextSize() {
        return tipTextSize;
    }

    public void setTipTextSize(int tipTextSize) {
        this.tipTextSize = tipTextSize;
    }

    public int getTipTextColor() {
        return tipTextColor;
    }

    public void setTipTextColor(int tipTextColor) {
        this.tipTextColor = tipTextColor;
    }

    public int getTipRadius() {
        return tipRadius;
    }

    public void setTipRadius(int tipRadius) {
        this.tipRadius = tipRadius;
    }

    public int getScrollLineColor() {
        return scrollLineColor;
    }

    public void setScrollLineColor(int scrollLineColor) {
        this.scrollLineColor = scrollLineColor;
    }

    public boolean isShowIcon() {
        return isShowIcon;
    }

    public void setShowIcon(boolean showIcon) {
        isShowIcon = showIcon;
    }

    public boolean isShowTip() {
        return isShowTip;
    }

    public void setShowTip(boolean showTip) {
        isShowTip = showTip;
    }

    public boolean isShowScrollLine() {
        return isShowScrollLine;
    }

    public void setShowScrollLine(boolean showScrollLine) {
        isShowScrollLine = showScrollLine;
    }

    public int[] getTipOffsetArr() {
        return tipOffsetArr;
    }

    public void setTipOffsetArr(int[] tipOffsetArr) {
        this.tipOffsetArr = tipOffsetArr;
    }

    public int getLeftRightMargin() {
        return leftRightMargin;
    }

    public void setLeftRightMargin(int leftRightMargin) {
        this.leftRightMargin = leftRightMargin;
    }
}
