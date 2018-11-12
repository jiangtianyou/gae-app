package com.app.modules.test;

import com.app.modules.common.bean.ReturnMsg;
import com.app.modules.notify.mail.OkayApiMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("test")
public class TestController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("")
	@ResponseBody
	public ReturnMsg sendMail() {
		String forObject = restTemplate.getForObject("http://hb5.api.okayapi.com/", String.class);
		return new ReturnMsg(ReturnMsg.Response.APP_OK,forObject);
	}
}
