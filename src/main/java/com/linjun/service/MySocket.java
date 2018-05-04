package com.linjun.service;

import com.linjun.conf.Const;
import com.linjun.handler.InitializerPipeline;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author 林俊
 * @create 2018/5/4.
 * @desc
 **/
public class MySocket implements Runnable{
    @Override
    public void run() {
        ServerBootstrap server = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new InitializerPipeline()).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true);
            ChannelFuture f = server.bind(Const.PORT).sync();
            System.out.println("SYSTEM - SERVER PORT: " + Const.PORT);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("SYSTEM ERROR: " + e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
