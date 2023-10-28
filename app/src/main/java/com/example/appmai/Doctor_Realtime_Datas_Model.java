package com.example.appmai;

public class Doctor_Realtime_Datas_Model {

    String name,email,status;

    public Doctor_Realtime_Datas_Model() {
    }

    public Doctor_Realtime_Datas_Model(String name, String email, String status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
