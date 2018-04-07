package com.linjun.controller;

import com.linjun.common.packets.HeartbeatBody;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author 林俊
 * @create 2018/3/25.
 * @desc
 **/
@Controller
public class WsController {
    @SendTo("/topic/getRespone")
    public HeartbeatBody say(HeartbeatBody heartbeatBody){
        return heartbeatBody;
    }
}
