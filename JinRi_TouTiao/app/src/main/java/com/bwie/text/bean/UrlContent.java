package com.bwie.text.bean;

import java.util.List;

/**
 * Created by Love_you on 2017/8/16 0016.
 */
public class UrlContent {
    private String title;
    private String url;
    private List<String> imagelist;//图片集合
    private boolean has_image;
    private boolean has_video;
    private String source;//专题
    private int filter_words;
    private String display_url;
    private String videourl;//视频的缩略图

    public UrlContent() {
    }
    public UrlContent(String title, String url, List<String> imagelist, boolean has_image, boolean has_video, String source, int filter_words, String display_url, String videourl) {
        this.title = title;
        this.url = url;
        this.imagelist = imagelist;
        this.has_image = has_image;
        this.has_video = has_video;
        this.source = source;
        this.filter_words = filter_words;
        this.display_url = display_url;
        this.videourl = videourl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getImagelist() {
        return imagelist;
    }

    public boolean isHas_image() {
        return has_image;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public String getSource() {
        return source;
    }

    public int getFilter_words() {
        return filter_words;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImagelist(List<String> imagelist) {
        this.imagelist = imagelist;
    }

    public void setHas_image(boolean has_image) {
        this.has_image = has_image;
    }

    public void setHas_video(boolean has_video) {
        this.has_video = has_video;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setFilter_words(int filter_words) {
        this.filter_words = filter_words;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    @Override
    public String toString() {
        return "UrlContent{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imagelist=" + imagelist +
                ", has_image=" + has_image +
                ", has_video=" + has_video +
                ", source='" + source + '\'' +
                ", filter_words=" + filter_words +
                ", display_url='" + display_url + '\'' +
                ", videourl='" + videourl + '\'' +
                '}';
    }
}
