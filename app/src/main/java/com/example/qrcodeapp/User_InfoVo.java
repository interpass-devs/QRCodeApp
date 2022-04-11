package com.example.qrcodeapp;

/**
 * Created by hsra on 2019-06-25.
 */

public class User_InfoVo {

    private String emp_id;
    private String emp_pw;

    public User_InfoVo(String emp_id, String emp_pw) {
        this.emp_id = emp_id;
        this.emp_pw = emp_pw;
    }



    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_pw() {
        return emp_pw;
    }

    public void setEmp_pw(String emp_pw) {
        this.emp_pw = emp_pw;
    }

    @Override
    public String toString() {
        return "User_InfoVo{" +
                ", emp_id='" + emp_id + '\'' +
                ", emp_pw='" + emp_pw + '\'' +
                '}';
    }
}
