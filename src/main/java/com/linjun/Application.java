package com.linjun;

import com.linjun.service.MySocket;
import com.linjun.service.MyWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 林俊
 * @create 2018/3/18.
 * @desc
 **/
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        MySocket mySocket=new MySocket();
        MyWebSocket myWebSocket=new MyWebSocket();
        Thread t1=new Thread(mySocket);
        Thread t2=new Thread(myWebSocket);
        t1.start();
        t2.start();
        SpringApplication.run(Application.class, args);
    }
}