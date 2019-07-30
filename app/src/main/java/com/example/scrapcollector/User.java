package com.example.scrapcollector;

public class User {
    private String id,name,username,password,email,contact,gender;
    public User(){

    }
    public User(String ID,String Name,String Username,String Password,String Email,String Contact,String Gender){
        this.id=ID;
        this.contact = Contact;
        this.email =Email;
        this.gender = Gender;
        this.name = Name;
        this.username = Username;
        this.password = Password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getGender() {
        return gender;
    }
}
