package com.app.modules.google;

import com.app.modules.common.bean.ReturnMsg;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;

@Controller
@RequestMapping("auth")
public class GoogleOauthController {

	UserService userService = UserServiceFactory.getUserService();

	@RequestMapping("")
	@ResponseBody
	public ReturnMsg login(HttpServletRequest request) {

		User user = userService.getCurrentUser();
		String userStr = null;
		if (user != null) {
			userStr = user.getUserId()+user.getAuthDomain()+user.getFederatedIdentity()+user.getNickname()+user.getUserId();
		}
		String url = request.getRequestURI();
		Principal userPrincipal = request.getUserPrincipal();
		String loginURL;
		String className = null;
		String name = null;
		if(userPrincipal == null){
			loginURL = userService.createLoginURL(url);
		}else{
			name = userPrincipal.getName();
			className = userPrincipal.getClass().getName();
		}
		return new ReturnMsg(ReturnMsg.Response.APP_OK,Arrays.asList(name,className,url,userStr));
	}
}
