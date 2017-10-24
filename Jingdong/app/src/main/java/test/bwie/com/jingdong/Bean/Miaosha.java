package test.bwie.com.jingdong.Bean;

/**
 * Created by Love_you on 2017/10/18 0018.
 */

public class Miaosha {
    public String image;//图片地址
    public String title;//介绍

    public Miaosha(String image, String title) {
        this.image = image;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
