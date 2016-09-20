package com.luno.pojo;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/20.
 */

public class ReaderInfo {

//    private Long libid;

    private String certId;

    private String readerType;

    private String dept;

    private String certFlag;

    private String name;

    public ReaderInfo(){}

    public ReaderInfo(String certId, String readerType, String dept, String certFlag, String name) {
        this.certId = certId;
        this.readerType = readerType;
        this.dept = dept;
        this.certFlag = certFlag;
        this.name = name;
    }

    //    public Long getLibid() {
//        return libid;
//    }
//
//    public void setLibid(Long libid) {
//        this.libid = libid;
//    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCertFlag() {
        return certFlag;
    }

    public void setCertFlag(String certFlag) {
        this.certFlag = certFlag;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReaderInfo{" +
                ", cretId='" + certId + '\'' +
                ", readerType='" + readerType + '\'' +
                ", dept='" + dept + '\'' +
                ", certFlag='" + certFlag + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
