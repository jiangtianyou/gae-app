package com.app.modules.common.utils;

import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.Map;

public class HtmlTemplateUtil {
	private final static TemplateEngine templateEngine = new TemplateEngine();

	/**
	 * 使用 Thymeleaf 渲染 HTML
	 * @param template  HTML模板相对于template
	 * @param params 参数
	 * @return  渲染后的HTML
	 */
	public static String render(String template, Map<String, Object> params){

		// for exampe:template = notify/test.html
		StringBuilder stringBuilder = new StringBuilder();
		try {
			File file = ResourceUtils.getFile("classpath:templates/"+template);
			InputStreamReader is = new InputStreamReader(new FileInputStream(file),"UTF-8");
			BufferedReader bf = new BufferedReader(is);
			String line;
			while ((line=bf.readLine()) != null){
				stringBuilder.append(line);
			}
			bf.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Context context = new Context();
		context.setVariables(params);
		return templateEngine.process(stringBuilder.toString(), context);
	}



}
