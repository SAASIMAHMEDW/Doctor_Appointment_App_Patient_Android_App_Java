package com.example.appmai;

public class Users {
    String Name, Age,Email, Password, Password_Hint,Gender,uid,phone_no,profile_pic_URL;

    public Users() {
    }

    public Users(String name, String age, String email, String password, String hint, String gender, String uid, String phone_no, String profile_pic_URL) {
        Name = name;
        Age = age;
        Email = email;
        Password = password;
        Password_Hint = hint;
        Gender = gender;
        this.uid = uid;
        this.phone_no = phone_no;
        this.profile_pic_URL = profile_pic_URL;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getProfile_pic_URL() {
        return profile_pic_URL;
    }

    public void setProfile_pic_URL(String profile_pic_URL) {
        this.profile_pic_URL = profile_pic_URL;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
       Gender= gender;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getHint() {
        return Password_Hint;
    }

    public String getAge() {
        return Age;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setHint(String hint) {
        Password_Hint = hint;
    }

    public void setAge(String age) {
        Age = age;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}






