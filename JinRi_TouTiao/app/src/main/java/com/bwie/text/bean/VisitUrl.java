package com.bwie.text.bean;

/**
 * Created by Love_you on 2017/8/16 0016.
 */
public class VisitUrl {
    private String title;
    private String path;

    public VisitUrl(String title, String path) {
        this.title = title;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "VisitUrl{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
