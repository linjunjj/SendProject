package com.linjun;

/**
 * @author 林俊
 * @create 2018/4/8.
 * @desc
 **/
public class MessageVO {
    private Integer userNum;


    private Integer type;


    private String message;

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
