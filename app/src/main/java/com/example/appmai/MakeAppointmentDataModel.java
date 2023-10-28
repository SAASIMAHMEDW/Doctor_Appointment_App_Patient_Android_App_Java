package com.example.appmai;

public class MakeAppointmentDataModel {

    String name,email,problem,status;

    public MakeAppointmentDataModel() {
    }

    public MakeAppointmentDataModel(String name, String email, String problem, String status) {
        this.name = name;
        this.email = email;
        this.problem = problem;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
