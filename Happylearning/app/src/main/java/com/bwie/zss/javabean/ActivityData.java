package com.bwie.zss.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/10/4  09：41
 */

public class ActivityData implements Serializable{

    /**
     * code : 0
     * msg : ok
     * data : {"id":"5281","title":"北京游学成长励志夏令营","child_price":"1380.00","adult_price":"0.00","status":"1","date_list":[{"time_id":"8437","price":"1380.00","date":"07月18日-07月21日","days":2,"start_time":"17:00","end_time":"17:00"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 5281
         * title : 北京游学成长励志夏令营
         * child_price : 1380.00
         * adult_price : 0.00
         * status : 1
         * date_list : [{"time_id":"8437","price":"1380.00","date":"07月18日-07月21日","days":2,"start_time":"17:00","end_time":"17:00"}]
         */

        private String id;
        private String title;
        private String child_price;
        private String adult_price;
        private String status;
        private List<DateListBean> date_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getChild_price() {
            return child_price;
        }

        public void setChild_price(String child_price) {
            this.child_price = child_price;
        }

        public String getAdult_price() {
            return adult_price;
        }

        public void setAdult_price(String adult_price) {
            this.adult_price = adult_price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DateListBean> getDate_list() {
            return date_list;
        }

        public void setDate_list(List<DateListBean> date_list) {
            this.date_list = date_list;
        }

        public static class DateListBean implements Serializable{
            /**
             * time_id : 8437
             * price : 1380.00
             * date : 07月18日-07月21日
             * days : 2
             * start_time : 17:00
             * end_time : 17:00
             */

            private String time_id;
            private String price;
            private String date;
            private int days;
            private String start_time;
            private String end_time;

            public String getTime_id() {
                return time_id;
            }

            public void setTime_id(String time_id) {
                this.time_id = time_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }
        }
    }
}
