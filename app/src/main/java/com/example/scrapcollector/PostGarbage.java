package com.example.scrapcollector;

public class PostGarbage {
    private String id,locked,username,name,address,locality,pincode,code,contact,paper,plastic,metal,quantity;
    public PostGarbage(){

    }
    public PostGarbage(String Id,String Locked,String Username,String Name,String Address,String Locality,String Pincode,String Code,String Contact,String Paper,String Plastic,String Metal,String Quantity){
        this.id = Id;
        this.locked = Locked;
        this.username = Username;
        this.name = Name;
        this.address = Address;
        this.locality = Locality;
        this.pincode = Pincode;
        this.code = Code;
        this.contact = Contact;
        this.paper = Paper;
        this.plastic = Plastic;
        this.metal = Metal;
        this.quantity = Quantity;

    }

    public String getName() {
        return name;
    }

    public String getLocked() {
        return locked;
    }

    public String getContact() {
        return contact;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCode() {
        return code;
    }

    public String getLocality() {
        return locality;
    }

    public String getMetal() {
        return metal;
    }

    public String getPaper() {
        return paper;
    }

    public String getPincode() {
        return pincode;
    }

    public String getPlastic() {
        return plastic;
    }

    public String getQuantity() {
        return quantity;
    }

}
