
package com.svw.dealerapp.entity;

/**
 * Created by ibm on 2016/4/21.
 */
public class DrawerMenuEntity {
    private String title;
    private int icon;

    public DrawerMenuEntity(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
