package com.zhmt.feibiao.httpserver.netty.mvc;

import com.zhmt.feibiao.httpserver.netty.Request;
import com.zhmt.feibiao.httpserver.netty.bean.Modle;

public interface Controller {
	Modle handleRequest(Request request) throws Exception;
}
