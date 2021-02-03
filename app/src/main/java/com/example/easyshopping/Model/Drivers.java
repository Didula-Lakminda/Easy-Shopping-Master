package com.example.easyshopping.Model;

public class Drivers {
    private String Name,Phone,Password,Identity_No,Vehicle_No,License_No;

    public Drivers()
    {

    }

    public Drivers(String name, String phone, String password, String identity_No, String vehicle_No, String license_No) {
        Name = name;
        Phone = phone;
        Password = password;
        Identity_No = identity_No;
        Vehicle_No = vehicle_No;
        License_No = license_No;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getIdentity_No() {
        return Identity_No;
    }

    public void setIdentity_No(String identity_No) {
        Identity_No = identity_No;
    }

    public String getVehicle_No() {
        return Vehicle_No;
    }

    public void setVehicle_No(String vehicle_No) {
        Vehicle_No = vehicle_No;
    }

    public String getLicense_No() {
        return License_No;
    }

    public void setLicense_No(String license_No) {
        License_No = license_No;
    }
}

