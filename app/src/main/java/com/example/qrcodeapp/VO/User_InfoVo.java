package com.example.qrcodeapp.VO;

/**
 * Created by hsra on 2019-06-25.
 */

public class User_InfoVo {

    private String user_no;
    private String user_id;
    private String user_pw;

    public User_InfoVo(String user_no, String user_id, String user_pw) {
        this.user_no = user_no;
        this.user_id = user_id;
        this.user_pw = user_pw;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }


    @Override
    public String toString() {
        return "User_InfoVo{" +
                ", user_no='" + user_no + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_pw='" + user_pw + '\'' +
                '}';
    }
}
