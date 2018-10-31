package com.app.modules.notify.ding.controller;


import com.alibaba.fastjson.JSONObject;
import com.app.modules.notify.ding.message.Message;
import com.app.modules.notify.ding.message.SendResult;
import com.app.modules.notify.ding.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class DingtalkService {
	@Autowired
	private RestTemplate restTemplate;

    public static final String CHATBOT_WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=2cb88a6beb8ae5c05759d7d0cc09c87e420c17b83662214fa8f896debdc9fece";
    public SendResult send(Message message) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity entity = new HttpEntity<String>(message.toJsonString(), headers);
        SendResult sendResult = new SendResult();
        String s = "";
        try {
            s = restTemplate.postForObject(CHATBOT_WEBHOOK, entity, String.class);
            JSONObject obj = JSONObject.parseObject(s);
            Integer errcode = obj.getInteger("errcode");
            sendResult.setErrorCode(errcode);
            sendResult.setErrorMsg(obj.getString("errmsg"));
            sendResult.setIsSuccess(errcode.equals(0));
        } catch (Exception e) {
            sendResult.setIsSuccess(false);
            sendResult.setErrorMsg("发送失败");
        }
        return sendResult;
    }


	public void sendTextMessageWithAtAll(String msg) throws Exception {
		TextMessage message = new TextMessage(msg);
		message.setIsAtAll(true);
		SendResult result = this.send(message);
		System.out.println(result);
	}
}


