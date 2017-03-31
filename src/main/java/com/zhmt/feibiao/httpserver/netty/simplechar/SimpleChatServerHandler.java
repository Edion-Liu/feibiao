package com.zhmt.feibiao.httpserver.netty.simplechar;

import com.zhmt.feibiao.httpserver.HttpServer;
import com.zhmt.feibiao.httpserver.netty.websocketchat.TextWebSocketFrameHandler;
import com.zhmt.feibiao.tools.ApplicationContextHelper;
import com.zhmt.feibiao.user.bean.Devices;
import com.zhmt.feibiao.user.service.DevicesService;
import com.zhmt.feibiao.user.service.UserService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 服务端 channel
 * 
 * @author waylau.com
 * @date 2015-2-16
 */

public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> { // (1)

    private static final Logger logger = LoggerFactory.getLogger(SimpleChatServerHandler.class);
	
	/**
	 * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
	 * A closed Channel is automatically removed from the collection,
	 */
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private DevicesService devicesService= (DevicesService)ApplicationContextHelper.getBean("devicesService");


    /**************************new***********************************/
    //保存设备channel
    public static HashMap<String,Channel>   DevicesChanel=new HashMap<String,Channel>();
    //保存手机chanel
    public static HashMap<String,Channel>   UsersChannel=new HashMap<String,Channel>();
    //设备和手机的映射关系  //设备，手机
    public static HashMap<String,String>    DevidAndphoneid=new HashMap<String,String>();

    //设备和手机的映射关系  //设备，手机
    public static HashMap<String,String>    DevidAndphoneid2=new HashMap<String,String>();
    int InitClenDevicethred=0; //初始化清理线程



    /********************end************************************/

   private class ClenDevicesstatus extends Thread{


             public void run(){
                 while (true)
                 {
                     try {
                         Thread.sleep(1000*60*5); //5分钟清理一次
                         List<Devices> dev=devicesService.getalldevices();
                         for(Devices devs:dev)
                         {
                             if(devs.getOnlinestatus().equals("在线"))
                             {
                                String strphone=DevidAndphoneid.get(devs.getDeviceid());
                                 if(strphone!=null)
                                 {

                                 }else {

                                     String strphone2=DevidAndphoneid2.get(devs.getDeviceid());
                                     if(strphone2!=null)
                                     {

                                     }else {

                                         //不在线
                                         devs.setOnlinestatus("掉线");
                                         devicesService.updevices(devs);
                                         logger.info("清理异常在线设备");
                                     }


                                 }
                             }
                         }


                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
    }

    private void phonechannel(Channel channel,String s)
    {

        String strphone=s.substring(7,18);

        if(strphone!=null)
            UsersChannel.put(strphone,channel);
        logger.info("手机设备ID长度:{}",strphone);
       // System.out.println("手机设备ID长度："+strphone);

    }


    private void devicesechannel(Channel channel,String s)
    {


        String strdevice=s.substring(7,15);

        DevicesChanel.put(strdevice,channel);


        Devices dev=devicesService.getcontrollerid(strdevice);

        dev.setOnlinestatus("在线");
        devicesService.updevices(dev);

        DevidAndphoneid.put(strdevice,dev.getControllerid());

        DevidAndphoneid2.put(strdevice,dev.getControllerid2());
        logger.info("硬件设备ID:{}",strdevice);

      //  System.out.println("硬件设备ID："+strdevice);


    }

    //设置新添加进来的channel,添加所有新增channel

    /**
     *
     * @param @channel   所有连接通道
     * @param s
     */
    private Boolean addnewchannel(ChannelHandlerContext ctx,String s)
    {
        Boolean status=false;


        int length=s.length();
        if((length==15)&&(s.subSequence(0,7).equals("DIDBind")))
        {//绑定设备
            devicesechannel(ctx.channel(),s);
            status=true;
        }

        if((length==18)&&(s.subSequence(0,7).equals("PIDBind")))
        {//绑定设备
            phonechannel(ctx.channel(),s);
            status=true;
        }

        return status;
    }


    //发送对应的数据到对应设备
    private void Sendtoid(Channel chan,String s)
    {
        int length=s.length();
        if(length<11)
            return;
        if(s.subSequence(0,3).equals("PID"))
        {
            String devid=s.substring(3,11);
            Channel C=DevicesChanel.get(devid);


            logger.info("发送channel：{}",C);
            logger.info("服务器下发到至硬件设备:{},发送数据:{}",devid,s);
           // System.out.println("发送channel:"+C);
          //  System.out.println("服务器下发到至硬件设备："+devid+",发送数据："+s);
            if(C!=null)
            {
                if(C.isOpen())
                    C.writeAndFlush(s+"\n");

            }


        }

        if(s.subSequence(0,3).equals("DID"))
        {
           //发送至手机  start
            String devid=s.substring(3,11);
            String phonenumber=DevidAndphoneid.get(devid);
            Channel C=UsersChannel.get(phonenumber);


            if(C!=null)
            {
                if(C.isOpen())
                {
                    C.writeAndFlush(s+"\n");
                    logger.info("服务器下发到至手机设备1:{},发送数据:{}",phonenumber,s);
                }


            }

            String phonenumber2=DevidAndphoneid2.get(devid);
            Channel C2=UsersChannel.get(phonenumber2);


            if(C2!=null)
            {
                if(C2.isOpen())
                {
                    C2.writeAndFlush(s+"\n");
                    logger.info("服务器下发到至手机设备2:{},发送数据:{}",phonenumber2,s);
                }


            }


            if((s.length()>=17)&&(s.substring(12,17).equals("GPRMC")))
            {//
                //更新gps信息
                String gps=s.substring(19);
                String []  gprmc=gps.split(",");
                if(gprmc[1].equals("A")) {

                    Devices dev = devicesService.getcontrollerid(devid);

                    dev.setGps(s);
                    devicesService.updevices(dev);
                }
            }
            //结束


            //发送至管理系统  start

            for (Channel channel : TextWebSocketFrameHandler.channelswebsocket) {

                   if(channel!=null)
                   {
                       if(channel.isOpen())
                       {
                           channel.writeAndFlush(new TextWebSocketFrame(s));
                       }
                   }


            }


            //结束


        }





    }

    public String get_DevicesChanel_KeyByValue(Channel channel)
    {
        //采用Iterator遍历channel_a,查看channel_a中是否存在对应绑定ID
        Iterator it = DevidAndphoneid.keySet().iterator();
        while(it.hasNext()) {



            String key = (String) it.next();
            Channel  value=DevicesChanel.get(key);

            if(value!=null)
            {

                if(value !=channel)
                {

                }else {

                    return key;
                }

            }


        }

        return null;
    }




    public String get_UsersChannel_KeyByValue(Channel channel)
    {
        //采用Iterator遍历channel_a,查看channel_a中是否存在对应绑定ID
        Iterator it = UsersChannel.keySet().iterator();
        while(it.hasNext()) {

            String key = (String) it.next();
            Channel  value=UsersChannel.get(key);


            if(value!=null)
            {

                if(value !=channel)
                {

                }else {
                    return key;
                }

            }


        }

        return null;
    }


    /**
     *  清除无效channel
     * @param channel
     */
   public void clearchannel(Channel  channel)
   {

       String keydev=get_DevicesChanel_KeyByValue(channel);
       if(keydev!=null)
       {
           DevicesChanel.remove(keydev);
           //关闭在线状态
           Devices dev=devicesService.getcontrollerid(keydev);
           dev.setOnlinestatus("掉线");
           devicesService.updevices(dev);
           DevidAndphoneid.remove(keydev);
           DevidAndphoneid2.remove(keydev);
       }


       String keydev2=get_UsersChannel_KeyByValue(channel);
       if(keydev2!=null)
       {
           UsersChannel.remove(keydev2);
       }


   }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        
        // Broadcast a message to multiple Channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
        logger.info("有连接加入进来了：{}",ctx.channel());
       // System.out.println("有连接加入进来了："+ctx.channel());
        channels.add(ctx.channel());
        if(InitClenDevicethred==0)
        {
            InitClenDevicethred=1;
            ClenDevicesstatus clenDevicesstatus=new ClenDevicesstatus();
            clenDevicesstatus.start();
        }



    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        clearchannel(incoming);
        
        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
        
        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }
    @Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception { // (4)
		Channel incoming = ctx.channel();
        Boolean FLAG=  addnewchannel(ctx,s);
        if(!FLAG)Sendtoid(incoming,s);
        logger.info("当前channel:{},服务器接收:{}",incoming,s);
      //  System.out.println("当前channel:"+incoming+",服务器接收："+s);

	}
  
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        logger.info("SimpleChatClient:{},在线",incoming.remoteAddress());
		//System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        clearchannel(incoming);
        logger.info("SimpleChatClient:{},掉线",incoming.remoteAddress());
		//System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
	}
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
    	Channel incoming = ctx.channel();
        logger.info("SimpleChatClient:{}，异常",incoming.remoteAddress());
		//System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");

        clearchannel(incoming);

        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}