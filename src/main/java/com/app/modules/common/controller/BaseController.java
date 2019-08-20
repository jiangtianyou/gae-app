package com.app.modules.common.controller;

import com.app.modules.generator.entity.ReturnT;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class BaseController {
	protected  Logger logger = Logger.getLogger(this.getClass().getName());


	@ExceptionHandler({Exception.class})
	@ResponseBody
	public ReturnT<String> handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		ReturnT<String> returnT = new ReturnT<>("");
		returnT.setCode(ReturnT.FAIL_CODE);
		returnT.setMsg(e.getMessage() == null ? "处理失败" : e.getMessage());
		return returnT;
	}
}
