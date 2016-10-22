package com.luno.pojo;

/**
 * Created by Administrator on 2016-10-22.
 */
public class LibBookNum {

    private Long libid ;
    private String isbn;
    private Integer pbooknum;
    private Integer ebooknum;

    public Long getLibid() {
        return libid;
    }

    public void setLibid(Long libid) {
        this.libid = libid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPbooknum() {
        return pbooknum;
    }

    public void setPbooknum(Integer pbooknum) {
        this.pbooknum = pbooknum;
    }

    public Integer getEbooknum() {
        return ebooknum;
    }

    public void setEbooknum(Integer ebooknum) {
        this.ebooknum = ebooknum;
    }
}
