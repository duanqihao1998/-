package com.bwie.text.bean;

/**
 * Created by Love_you on 2017/8/16 0016.
 */
public class SlidingBean {
    private int slidingIcom;
    private String slidingText;

    public SlidingBean(int slidingIcom, String slidingText) {
        this.slidingIcom = slidingIcom;
        this.slidingText = slidingText;
    }

    public int getSlidingIcom() {
        return slidingIcom;
    }

    public String getSlidingText() {
        return slidingText;
    }

    public void setSlidingIcom(int slidingIcom) {
        this.slidingIcom = slidingIcom;
    }

    public void setSlidingText(String slidingText) {
        this.slidingText = slidingText;
    }

    @Override
    public String toString() {
        return "SlidingBean{" +
                "slidingIcom=" + slidingIcom +
                ", slidingText='" + slidingText + '\'' +
                '}';
    }
}
