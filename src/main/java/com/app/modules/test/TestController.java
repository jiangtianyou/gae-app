package com.app.modules.test;

import com.app.modules.common.bean.ReturnMsg;
import com.app.modules.notify.mail.OkayApiMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mail")
public class TestController {
	@Autowired
	private OkayApiMailService service;

	@RequestMapping("send")
	@ResponseBody
	public ReturnMsg sendMail() {
		try {
			service.sendMail("420671626@qq.com", "测试", "测试内容");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.Response.APP_NG);
		}
		return new ReturnMsg(ReturnMsg.Response.APP_OK);
	}
}
