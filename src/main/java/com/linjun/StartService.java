package com.linjun;

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
 * @create 2018/5/2.
 * @desc
 **/
public class StartService {
    static int PORT = 8080;
    public void initialize() {
        ServerBootstrap server = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new InitializerPipeline()).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true);
            ChannelFuture f = server.bind(PORT).sync();
            System.out.println("SYSTEM - SERVER PORT: " + PORT);
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            System.out.println("SYSTEM ERROR: " + e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * 应用程序入口.
     */
    public static void main(String[] args) {

        if (args != null && args.length != 0) {
            try {
                 PORT= Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        new StartService().initialize();
    }
}
