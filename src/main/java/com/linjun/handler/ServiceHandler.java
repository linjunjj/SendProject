package com.linjun.handler;

import com.linjun.SendPacket;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @author 林俊
 * @create 2018/5/2.
 * @desc
 **/

public class ServiceHandler extends SimpleChannelInboundHandler<Object> {
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel incoming = ctx.channel();

        if (msg instanceof SendPacket) {
            SendPacket em = (SendPacket) msg;
            System.out.println("RECEIVED: " + ctx.channel().remoteAddress() + " " + em.getJingdu());
        }
        ctx.channel().writeAndFlush(new TextWebSocketFrame("来自服务端: " + LocalDateTime.now()));
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

}

