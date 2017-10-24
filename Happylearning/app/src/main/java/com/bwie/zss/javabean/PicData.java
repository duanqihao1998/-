package com.bwie.zss.javabean;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/10/3  14：26
 */

public class PicData {

    /**
     * code : 0
     * data : {"name":"temp.png","pic":"http://img1.2hbook.cn/lexue365/Img/2017-10-03/59d32d2039f09.png","size":"483.82k"}
     * msg : 上传成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * name : temp.png
         * pic : http://img1.2hbook.cn/lexue365/Img/2017-10-03/59d32d2039f09.png
         * size : 483.82k
         */

        private String name;
        private String pic;
        private String size;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
