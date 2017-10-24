package com.bwie.zss.javabean;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/10/5  23：14
 */

public class UpdateUser {

    /**
     * code : 0
     * data : {"birthday":"1996","city_id":"2","district_id":"220","headimgurl":"http://img1.2hbook.cn/lexue365/Img/2017-10-05/59d64bee19891.png","nickname":"棒棒糖","realname":"赵姗杉","sex":"女"}
     * msg : 修改个人信息成功
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
         * birthday : 1996
         * city_id : 2
         * district_id : 220
         * headimgurl : http://img1.2hbook.cn/lexue365/Img/2017-10-05/59d64bee19891.png
         * nickname : 棒棒糖
         * realname : 赵姗杉
         * sex : 女
         */

        private String birthday;
        private String city_id;
        private String district_id;
        private String headimgurl;
        private String nickname;
        private String realname;
        private String sex;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(String district_id) {
            this.district_id = district_id;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
