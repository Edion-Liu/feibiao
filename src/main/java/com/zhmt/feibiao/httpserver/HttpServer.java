package com.zhmt.feibiao.httpserver;

import com.zhmt.feibiao.httpserver.netty.simplechar.SimpleChatServer;
import com.zhmt.feibiao.httpserver.netty.websocketchat.WebsocketChatServer;
import com.zhmt.feibiao.tools.ApplicationContextHelper;
import com.zhmt.feibiao.user.service.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhmt.feibiao.httpserver.netty.ControllerMap;
import com.zhmt.feibiao.httpserver.netty.HttpServerHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * start http server
 * 
 *
 */
public class HttpServer {
	private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
	/** listening port **/
	private int port;
	/** controller mapping **/
	private ControllerMap controllerMap;

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(new HttpRequestDecoder());
					// pipeline.addLast(new HttpObjectAggregator(1024 * 1024 *
					// 64));//for FullHttpRequest
					pipeline.addLast(new HttpServerCodec());
					pipeline.addLast(new HttpServerHandler(controllerMap));
					pipeline.addLast(new HttpResponseEncoder());
				}
			}).option(ChannelOption.SO_BACKLOG, 1024);

			ChannelFuture f = b.bind(port).sync();

/*            List<Integer> ports = Arrays.asList(8080, 8081);
            Collection<Channel> channels = new ArrayList<>(ports.size());
            for (int port : ports) {
                Channel serverChannel = b.bind(port).sync().channel();
                channels.add(serverChannel);
            }
            for (Channel ch : channels) {
                ch.closeFuture().sync();
            }*/


            logger.info("HTTP服务启动了 port:{}", port);
            //开启netty tcp socket 连接
            new SimpleChatServer(7397).run();


		//	logger.info("====>>>>>>>>>>>>>>>>Http Server is running on port:{}", port);
			f.channel().closeFuture().sync();



		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public void start() {
		try {
			run();

			logger.info("====>>>>>>>>>>>>>>>>Http Server started successfully");
		} catch (Exception e) {
			logger.error("====>>>>>>>>>>>>>>>>Http Server start failed: ", e);
		}
	}

	public static void main(String[] args) {






        try {
			@SuppressWarnings({ "unused", "resource" })

            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");


			//new ClassPathXmlApplicationContext("applicationContext.xml");

			//UserService userService= (UserService) ApplicationContextHelper.getBean("userService");

			//System.out.println("ooooooooooo"+userService);


			logger.info("====>>>>>>>>>>>>>>>> Server started successfully");

		} catch (Exception e) {
			logger.error("====>>>>>>>>>>>>>>>> Server start failed: ", e);
			System.exit(-1);
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ControllerMap getControllerMap() {
		return controllerMap;
	}

	public void setControllerMap(ControllerMap controllerMap) {
		this.controllerMap = controllerMap;
	}

}
