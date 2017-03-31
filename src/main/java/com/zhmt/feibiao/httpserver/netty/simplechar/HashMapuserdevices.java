package com.zhmt.feibiao.httpserver.netty.simplechar;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/10/5 0005.
 */
public class HashMapuserdevices {

    private String   userid;

    Set<Channel> set=new HashSet<Channel>();


  //  private ArrayList<Channel> channelsuser=new ArrayList<Channel>();


   // public  static HashMap<String,Channel> hashMapuserdevices=new HashMap<String,Channel>();

    public void addChannel(Channel channel)
    {
       // channelsuser.add(channel);
        set.add(channel);
    }

    public void remove(Channel channel)
    {

        //channelsuser.remove(channel);
        set.remove(channel);
    }



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

  /*  public void puthashMapuserdevices(String s,Channel channel)
    {
        hashMapuserdevices.put(s,channel);
    }

    public void gethashMapuserdevices(String s)
    {
        hashMapuserdevices.get(s);
    }

    public static HashMap<String, Channel> getHashMapuserdevices() {
        return hashMapuserdevices;
    }

    public static void setHashMapuserdevices(HashMap<String, Channel> hashMapuserdevices) {
        HashMapuserdevices.hashMapuserdevices = hashMapuserdevices;
    }*/
}
