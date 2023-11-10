package com.example.appmai;

public class UserAppointmentModel {

    String name,email,phoneno,problem,status;

    public UserAppointmentModel() {
    }

    public UserAppointmentModel(String name, String email, String phoneno, String problem) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.problem = problem;
    }

    public UserAppointmentModel(String name, String email, String phoneno, String problem, String status) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.problem = problem;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
