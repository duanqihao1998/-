package test.bwie.com.jingdong.Bean;

/**
 * Created by Love_you on 2017/10/18 0018.
 */

public class TuiJianBean {
    public String images;
    public String title;
    public String price;

    public TuiJianBean(String images, String title, String price) {
        this.images = images;
        this.title = title;
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
