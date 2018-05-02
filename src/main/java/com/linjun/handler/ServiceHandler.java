package com.linjun.handler;

import com.linjun.SendPacket;
import com.linjun.conf.NettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author 林俊
 * @create 2018/5/2.
 * @desc
 **/

public class ServiceHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker webSocketServerHandshaker;
    private static final String WEB_SOCKET_URL = "ws://localhost:8888/webSocket";
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static double d;
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel incoming = ctx.channel();

        if (msg instanceof SendPacket) {
            SendPacket em = (SendPacket) msg;
           d=em.getJingdu();
            System.out.println("RECEIVED: " + ctx.channel().remoteAddress() + " " + em.getJingdu());
            handWebSocketFrame(ctx,(WebSocketFrame) msg);

        }
    }



    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        System.out.println("SYSTEM CHANNEL REMOVED: " + incoming.remoteAddress());

        for (Channel channel : channels) {
            if (channel == incoming)
                incoming.writeAndFlush("你已离线");
            else
                channel.writeAndFlush(incoming.remoteAddress() + " 离线");
        }

        channels.remove(incoming);
        System.out.println("SYSTEM CHANNEL SIZE: " + channels.size());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        System.out.println("SYSTEM CHANNEL ADDED: " + incoming.remoteAddress());

        channels.add(incoming);
        System.out.println("SYSTEM CHANNEL SIZE: " + channels.size());
        for (Channel channel : channels) {
            if (channel == incoming)
                incoming.writeAndFlush("连接成功");
            else
                channel.writeAndFlush(incoming.remoteAddress() + " 已在线\n");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel incoming = ctx.channel();
        ctx.writeAndFlush(incoming.remoteAddress() + " 加入成功\n");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        ctx.writeAndFlush(incoming.remoteAddress() + " 推出\n");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.writeAndFlush("系统错误" + cause.getMessage());
        ctx.close();
    }

    private void handWebSocketFrame(ChannelHandlerContext context,WebSocketFrame webSocketFrame){
        if (webSocketFrame instanceof CloseWebSocketFrame){//判断是否是关闭websocket的指令
            webSocketServerHandshaker.close(context.channel(),(CloseWebSocketFrame) webSocketFrame.retain());
        }
        if (webSocketFrame instanceof PingWebSocketFrame){//判断是否是ping消息
            context.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }
        if (!(webSocketFrame instanceof TextWebSocketFrame)){//判断是否是二进制消息
            System.out.println("不支持二进制消息");
            throw new RuntimeException(this.getClass().getName());
        }
        //返回应答消息
        //获取客户端向服务端发送的消息
        String request = ((TextWebSocketFrame) webSocketFrame ).text();
        System.out.println("服务端收到客户端的消息：" + request);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(context.channel().id() + ":" + request);
        //服务端向每个连接上来的客户端发送消息
        NettyConfig.group.writeAndFlush(textWebSocketFrame);
    }






}

