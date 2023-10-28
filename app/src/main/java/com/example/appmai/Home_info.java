package com.example.appmai;

public class Home_info {
  String edit_text_name;
  String doctor_email;

    public String getEdit_text_name() {
        return edit_text_name;
    }

    public void setEdit_text_name(String edit_text_name) {
        this.edit_text_name = edit_text_name;
    }


    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public Home_info(String edit_text_name , String doctor_email) {

        this.edit_text_name = edit_text_name;
        this.doctor_email = doctor_email;


    }




}
