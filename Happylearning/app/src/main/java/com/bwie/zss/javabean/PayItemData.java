package com.bwie.zss.javabean;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/10/7  20：44
 */

public class PayItemData {
    String title;
    String content;
    int image;
    boolean checked;

    public PayItemData(String title, String content, int image, boolean checked) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.checked = checked;
    }

    public PayItemData() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
