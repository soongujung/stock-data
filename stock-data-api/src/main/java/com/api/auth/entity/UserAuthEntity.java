package com.api.auth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "temp_users")
public class UserAuthEntity {

    @Id @GeneratedValue
    private String nId;

    private String vUserName;

    private String vPassword;

    private String vEmail;

    private String vSnsVender;

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getvUserName() {
        return vUserName;
    }

    public void setvUserName(String vUserName) {
        this.vUserName = vUserName;
    }

    public String getvPassword() {
        return vPassword;
    }

    public void setvPassword(String vPassword) {
        this.vPassword = vPassword;
    }

    public String getvEmail() {
        return vEmail;
    }

    public void setvEmail(String vEmail) {
        this.vEmail = vEmail;
    }

    public String getvSnsVender() {
        return vSnsVender;
    }

    public void setvSnsVender(String vSnsVender) {
        this.vSnsVender = vSnsVender;
    }
}
