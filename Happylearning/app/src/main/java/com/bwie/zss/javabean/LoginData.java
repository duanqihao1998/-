package com.bwie.zss.javabean;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/9/28  22：14
 */

public class LoginData {

    /**
     * code : 0
     * data : {"cltid":"1","mobile":"17611270963","referer_url":"","token":"87148c58cfe0ce7b7a0a25606ea13b53","userid":"666"}
     * msg : 注册成功
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
         * cltid : 1
         * mobile : 17611270963
         * referer_url :
         * token : 87148c58cfe0ce7b7a0a25606ea13b53
         * userid : 666
         */

        private String cltid;
        private String mobile;
        private String referer_url;
        private String token;
        private String userid;

        public String getCltid() {
            return cltid;
        }

        public void setCltid(String cltid) {
            this.cltid = cltid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getReferer_url() {
            return referer_url;
        }

        public void setReferer_url(String referer_url) {
            this.referer_url = referer_url;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
