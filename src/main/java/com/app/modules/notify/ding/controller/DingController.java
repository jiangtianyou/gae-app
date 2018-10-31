package com.app.modules.notify.ding.controller;

import com.app.modules.common.bean.ReturnMsg;
import com.app.modules.notify.ding.message.LinkMessage;
import com.app.modules.notify.ding.message.SendResult;
import com.app.modules.notify.ding.message.TextMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ding")
public class DingController {
	@Autowired
	DingtalkService dingtalkService;

	@RequestMapping("")
	@ResponseBody
	public ReturnMsg sendDing(String msg, HttpServletResponse response) {
		try {
			sendTextMessageWithAtAll(msg==null?"测试信息":msg);
			return new ReturnMsg(ReturnMsg.Response.APP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.Response.APP_NG,e.getMessage());
		}
	}

    @RequestMapping("/sendUrl")
    @ResponseBody
    public ReturnMsg dinguUrl() {
        try {
            sendLinkMessage();
            return new ReturnMsg(ReturnMsg.Response.APP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnMsg(ReturnMsg.Response.APP_NG,e.getMessage());
        }
    }


	@RequestMapping("/network")
	@ResponseBody
	public ReturnMsg network(String url) throws IOException {
		if(StringUtils.isBlank(url)){
			return new ReturnMsg(ReturnMsg.Response.APP_NG.getCode(),"url不能为空");
		}

		Map data = new HashMap();
//
//		URL urlStr = new URL(url);
//		HttpURLConnection conn = (HttpURLConnection) urlStr.openConnection();
//		conn.setConnectTimeout(2000);// 2秒则超时
//		conn.setReadTimeout(2000);
//		int code = conn.getResponseCode();
//		data.put("statusCode",code);
//		data.put("msg","能够连接到");

//		// 尝试连接ip.sb获取ip
		RestTemplate restTemplate = new RestTemplate();

//		ResponseEntity<String> entity = restTemplate.getForEntity("https://api.ip.sb/ip", String.class);
		ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
		String msg = entity.getBody();
		data.put("statusCode",entity.getStatusCode().value());
		data.put("msg",msg==null?"空":msg);


		return new ReturnMsg(ReturnMsg.Response.APP_OK,data);

	}







	private void sendTextMessageWithAtAll(String msg) throws Exception {
		TextMessage message = new TextMessage(msg);
		message.setIsAtAll(true);
		SendResult result = dingtalkService.send(message);
		System.out.println(result);
	}

	private void sendLinkMessage() throws Exception {
		LinkMessage message = new LinkMessage();
		message.setTitle("时代的火车蒋天佑");
		message.setText("这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”。\n" +
				"而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林”？\"");
		message.setMessageUrl("https://mp.weixin.qq.com/s?spm=a219a.7629140.0.0.EUDyWG&__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");
		message.setPicUrl("https://img.alicdn.com/tps/TB1XLjqNVXXXXc4XVXXXXXXXXXX-170-64.png");

		SendResult result = dingtalkService.send(message);
		System.out.println(result);
	}



}
