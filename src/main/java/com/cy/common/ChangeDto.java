package com.cy.common;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
public class ChangeDto implements Serializable{
    private String portname;
    private Integer baudrate;
    private Integer databits;
    private Integer stopbits;

    public String getPortname() {
        return portname;
    }

    public void setPortname(String portname) {
        this.portname = portname;
    }

    public Integer getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(Integer baudrate) {
        this.baudrate = baudrate;
    }

    public Integer getDatabits() {
        return databits;
    }

    public void setDatabits(Integer databits) {
        this.databits = databits;
    }

    public Integer getStopbits() {
        return stopbits;
    }

    public void setStopbits(Integer stopbits) {
        this.stopbits = stopbits;
    }
}
