package com.zhmt.feibiao.httpserver.netty.websocketchat;


import com.zhmt.feibiao.httpserver.netty.simplechar.SimpleChatServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 处理TextWebSocketFrame
 * 
 * @author waylau.com
 * 2015年3月26日
 */
public class TextWebSocketFrameHandler extends
		SimpleChannelInboundHandler<TextWebSocketFrame> {
	
	public static ChannelGroup channelswebsocket = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    //发送对应的数据到对应设备
    private void Sendtoid(Channel chan,String s)
    {
        Channel incoming = chan;
        int length=s.length();
        if(length<11)
            return;
        if(s.subSequence(0,3).equals("PID"))
        {
            String devid=s.substring(3,11);
            Channel C= SimpleChatServerHandler.DevicesChanel.get(devid);
            System.out.println("发送channel:"+C);
            System.out.println("服务器下发到至硬件设备："+devid+",发送数据："+s);
            if(C!=null)
            {
                if(C.isOpen())
                    C.writeAndFlush(s+"\n");

            }


        }

        if(s.subSequence(0,3).equals("DID"))
        {



           /* String devid=s.substring(3,7);
            String phonenumber=SimpleChatServerHandler.DevidAndphoneid.get(devid);
            Channel C=SimpleChatServerHandler.UsersChannel.get(phonenumber);
            //String  bodydata=s.substring(7,s.length());
            System.out.println("服务器下发到至手机设备："+phonenumber+",发送数据："+s);

            if(C!=null)
            {
                if(C.isOpen())
                    C.writeAndFlush(s+"\n");

            }*/


        }


    }
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			TextWebSocketFrame msg) throws Exception { // (1)


        Sendtoid(ctx.channel(),msg.text());



		System.out.println("websocket服务器接收信息:"+msg.text());
	}
	
	@Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        
        // Broadcast a message to multiple Channels
		channelswebsocket.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));

		channelswebsocket.add(incoming);
		System.out.println("Client:"+incoming.remoteAddress() +"加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        
        // Broadcast a message to multiple Channels
		channelswebsocket.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));
        
		System.out.println("Client:"+incoming.remoteAddress() +"离开");

        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }
	    
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"掉线");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	// (7)
			throws Exception {
    	Channel incoming = ctx.channel();
		System.out.println("Client:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
	}

}
