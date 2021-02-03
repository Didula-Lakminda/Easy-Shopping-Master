package com.example.easyshopping.Model;

public class Driversapplycationinfo {

    private String ApplyName,ApplyPhone,ApplyPassword,ApplyIdentity_No,ApplyVehicle_No,ApplyLicense_No;

    public Driversapplycationinfo() {
    }

    public Driversapplycationinfo(String applyName, String applyPhone, String applyPassword, String applyIdentity_No, String applyVehicle_No, String applyLicense_No) {
        ApplyName = applyName;
        ApplyPhone = applyPhone;
        ApplyPassword = applyPassword;
        ApplyIdentity_No = applyIdentity_No;
        ApplyVehicle_No = applyVehicle_No;
        ApplyLicense_No = applyLicense_No;
    }

    public String getApplyName() {
        return ApplyName;
    }

    public void setApplyName(String applyName) {
        ApplyName = applyName;
    }

    public String getApplyPhone() {
        return ApplyPhone;
    }

    public void setApplyPhone(String applyPhone) {
        ApplyPhone = applyPhone;
    }

    public String getApplyPassword() {
        return ApplyPassword;
    }

    public void setApplyPassword(String applyPassword) {
        ApplyPassword = applyPassword;
    }

    public String getApplyIdentity_No() {
        return ApplyIdentity_No;
    }

    public void setApplyIdentity_No(String applyIdentity_No) {
        ApplyIdentity_No = applyIdentity_No;
    }

    public String getApplyVehicle_No() {
        return ApplyVehicle_No;
    }

    public void setApplyVehicle_No(String applyVehicle_No) {
        ApplyVehicle_No = applyVehicle_No;
    }

    public String getApplyLicense_No() {
        return ApplyLicense_No;
    }

    public void setApplyLicense_No(String applyLicense_No) {
        ApplyLicense_No = applyLicense_No;
    }
}
