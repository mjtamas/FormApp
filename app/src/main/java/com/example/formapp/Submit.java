package com.example.formapp;

public class Submit {
    private String name,mail;

    public Submit(){

    }

    public Submit(String name, String mail){
        this.name=name;
        this.mail=mail;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
