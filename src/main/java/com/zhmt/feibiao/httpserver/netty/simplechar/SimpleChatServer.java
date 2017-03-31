package com.zhmt.feibiao.httpserver.netty.simplechar;

import com.zhmt.feibiao.httpserver.netty.websocketchat.WebsocketChatServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 简单聊天服务器-服务端
 * 
 * @author waylau.com
 * @date 2015-2-16
 */
public class SimpleChatServer {

    private int porttcp;

    public SimpleChatServer(int port) {
        this.porttcp = port;
    }

    public void run() throws Exception {
        
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new SimpleChatServerInitializer())  //(4)
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            

    		
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(porttcp).sync(); // (7)
            System.out.println("SimpleChatServer tcp服务 启动了"+porttcp);

            //开启websocket 连接
            new WebsocketChatServer(8888).run();

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            
    		System.out.println("SimpleChatServer 关闭了");
        }
    }

/*    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 7397;
        }
        new SimpleChatServer(port).run();

    }*/
}