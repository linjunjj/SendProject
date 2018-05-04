package com.linjun;
import com.linjun.service.MySocket;
import com.linjun.service.MyWebSocket;
/**
 * @author 林俊
 * @create 2018/5/2.
 * @desc
 **/
public class StartService {

    /**
     * 应用程序入口.
     */
    public static void main(String[] args) {
        MySocket mySocket=new MySocket();
        MyWebSocket myWebSocket=new MyWebSocket();
        Thread t1=new Thread(mySocket);
        Thread t2=new Thread(myWebSocket);
        t1.start();
        t2.start();
    }
}
