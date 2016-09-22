package com.luno.pojo;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ReaderPwd {

    private Long libid ;

    private String certId;

    private String password;

    public Long getLibid() {
        return libid;
    }

    public void setLibid(Long libid) {
        this.libid = libid;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ReaderPwd{" +
                "libid=" + libid +
                ", certId='" + certId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
