package com.roshanjha.loginscreenmd;

public class UserInformation {

    public String userName;
    public String address;
    public String email;
    public String mobile;
    public String bloodGrp;
    public String lastDonated;

    public UserInformation(){

    }

    public UserInformation(String userName, String address, String email, String mobile, String bloodGrp, String lastDonated) {
        this.userName = userName;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.bloodGrp = bloodGrp;
        this.lastDonated = lastDonated;
    }

    public UserInformation(String userName, String address, String mobile, String bloodGrp, String lastDonated) {
        this.userName = userName;
        this.address = address;
        this.mobile = mobile;
        this.bloodGrp = bloodGrp;
        this.lastDonated = lastDonated;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBloodGrp() {
        return bloodGrp;
    }

    public void setBloodGrp(String bloodGrp) {
        this.bloodGrp = bloodGrp;
    }

    public String getLastDonated() {
        return lastDonated;
    }

    public void setLastDonated(String lastDonated) {
        this.lastDonated = lastDonated;
    }
}
