package com.example.appmai;

public class ListFragmentRecyclerViewAdapterModel {

    String doc_name,doc_email,doc_phoneNo,doc_gender,doc_profile_pic_url;

    public ListFragmentRecyclerViewAdapterModel() {
    }

    public ListFragmentRecyclerViewAdapterModel(String doc_name, String doc_email, String doc_phoneNo, String doc_gender, String doc_profile_pic_url) {
        this.doc_name = doc_name;
        this.doc_email = doc_email;
        this.doc_phoneNo = doc_phoneNo;
        this.doc_gender = doc_gender;
        this.doc_profile_pic_url = doc_profile_pic_url;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public void setDoc_email(String doc_email) {
        this.doc_email = doc_email;
    }

    public String getDoc_phoneNo() {
        return doc_phoneNo;
    }

    public void setDoc_phoneNo(String doc_phoneNo) {
        this.doc_phoneNo = doc_phoneNo;
    }

    public String getDoc_gender() {
        return doc_gender;
    }

    public void setDoc_gender(String doc_gender) {
        this.doc_gender = doc_gender;
    }

    public String getDoc_profile_pic_url() {
        return doc_profile_pic_url;
    }

    public void setDoc_profile_pic_url(String doc_profile_pic_url) {
        this.doc_profile_pic_url = doc_profile_pic_url;
    }

    //  String edit_text_name;
//  String doctor_email;
//
//    public String getEdit_text_name() {
//        return edit_text_name;
//    }
//
//    public void setEdit_text_name(String edit_text_name) {
//        this.edit_text_name = edit_text_name;
//    }
//
//
//    public String getDoctor_email() {
//        return doctor_email;
//    }
//
//    public void setDoctor_email(String doctor_email) {
//        this.doctor_email = doctor_email;
//    }
//
//    public ListFragmentRecyclerViewAdapterModel(String edit_text_name , String doctor_email) {
//
//        this.edit_text_name = edit_text_name;
//        this.doctor_email = doctor_email;
//
//
//    }




}
