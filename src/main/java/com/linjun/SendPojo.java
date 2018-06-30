package com.linjun;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author 林俊
 * @create 2018/6/8.
 * @desc
 **/
public class SendPojo implements Serializable {
    private static final long serialVersionUID = 4010249994097151671L;
    private String deviceid;
    private double jingdu;
    private double weidu;
    private double x;
    private double y;
    private float speed;
    private String context;
    private byte[] bytes;
    private String send_uid;
    private String receive_uid;
    private long send_time;
    private long receive_time;
    private int sumCountPackage;
    private int countPackage;

    public SendPojo() {
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getSumCountPackage() {
        return this.sumCountPackage;
    }

    public void setSumCountPackage(int sumCountPackage) {
        this.sumCountPackage = sumCountPackage;
    }

    public int getCountPackage() {
        return this.countPackage;
    }

    public void setCountPackage(int countPackage) {
        this.countPackage = countPackage;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getSend_uid() {
        return this.send_uid;
    }

    public void setSend_uid(String send_uid) {
        this.send_uid = send_uid;
    }

    public String getReceive_uid() {
        return this.receive_uid;
    }

    public void setReceive_uid(String receive_uid) {
        this.receive_uid = receive_uid;
    }

    public long getSend_time() {
        return this.send_time;
    }

    public void setSend_time(long send_time) {
        this.send_time = send_time;
    }

    public long getReceive_time() {
        return this.receive_time;
    }

    public void setReceive_time(long receive_time) {
        this.receive_time = receive_time;
    }

    public String getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public double getJingdu() {
        return this.jingdu;
    }

    public void setJingdu(double jingdu) {
        this.jingdu = jingdu;
    }

    public double getWeidu() {
        return this.weidu;
    }

    public void setWeidu(double weidu) {
        this.weidu = weidu;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String toString() {
        return "SendPacket{deviceid='" + this.deviceid + '\'' + ", jingdu=" + this.jingdu + ", weidu=" + this.weidu + ", speed=" + this.speed + ", context='" + this.context + '\'' + ", bytes=" + Arrays.toString(this.bytes) + ", send_uid='" + this.send_uid + '\'' + ", receive_uid='" + this.receive_uid + '\'' + ", send_time=" + this.send_time + ", receive_time=" + this.receive_time + ", sumCountPackage=" + this.sumCountPackage + ", countPackage=" + this.countPackage + '}';
    }
}
