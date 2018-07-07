package com.roshanjha.loginscreenmd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HospitalUserInformation extends AppCompatActivity {

    public String hospitalName;
    public String address;
    public String mailid;
    public String mobile;
    public String avgBloodReqd;


    public HospitalUserInformation(){

    }

    public HospitalUserInformation(String hospitalName, String address, String mailid, String mobile, String avgBloodReqd) {
        this.hospitalName = hospitalName;
        this.address = address;
        this.mailid = mailid;
        this.mobile = mobile;
        this.avgBloodReqd = avgBloodReqd;
    }

    public HospitalUserInformation(String hospitalName, String address, String mobile, String avgBldReqd) {
        this.hospitalName = hospitalName;
        this.address = address;
        this.mobile = mobile;
        this.avgBloodReqd = avgBldReqd;

    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvgBloodReqd() {
        return avgBloodReqd;
    }

    public void setAvgBloodReqd(String avgBloodReqd) {
        this.avgBloodReqd = avgBloodReqd;
    }

}
