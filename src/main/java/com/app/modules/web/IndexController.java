package com.app.modules.web;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.app.modules.common.controller.BaseController;
import com.app.modules.common.utils.CodeGeneratorTool;
import com.app.modules.common.utils.FreemarkerTool;
import com.app.modules.common.utils.MyZip;
import com.app.modules.generator.entity.ClassInfo;
import com.app.modules.generator.entity.FieldInfo;
import com.app.modules.generator.entity.ReturnT;
import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseController {
	private Log log = LogFactory.get(this.getClass());

	@Autowired
	private FreemarkerTool freemarkerTool;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/genCode")
	@ResponseBody
	public ReturnT<Map<String, String>> codeGenerate(String tableSql, String packageName,String isJoin,String hasApi) throws Exception {

		if (StringUtils.isBlank(tableSql)) {
			return new ReturnT<>(ReturnT.FAIL_CODE, "表结构信息不可为空");
		}
		Map<String, String> result = generateDataMap(tableSql, packageName, isJoin,hasApi);
		return new ReturnT<>(result);

	}


	@RequestMapping("download")
	public void download(String tableSql,String packageName, String isJoin,String hasApi, HttpServletResponse res) throws Exception {

		ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql);
		Map<String, String> map = generateDataMap(tableSql, packageName, isJoin,hasApi);

		Map<String, String> fileNamesMap = fileNamesMap(classInfo.getClassName());
		List<MyZip.MemoryFile> memoryFiles = Lists.newArrayList();
		for (String key : map.keySet()) {
			String fileName = fileNamesMap.get(key);
			byte[] content = map.get(key).getBytes(Charsets.UTF_8);
			if (StringUtils.isNotBlank(fileName)) {
				memoryFiles.add(new MyZip.MemoryFile(fileName, content));
			}
		}
		byte[] zipByteArray = MyZip.createZipByteArray(memoryFiles);

		res.setContentType("application/octet-stream");
		res.addHeader("Content-Length", "" + zipByteArray.length);
		res.addHeader("Content-Disposition", "attachment;filename=source.zip");

		StreamUtils.copy(zipByteArray, res.getOutputStream());
	}

	private Map<String, String> generateDataMap(String tableSql, String packageName, String isJoin,String hasApi) throws Exception {
		Map<String, String> result = new HashMap<>();

		//mybatis 需要 del_flag 等字段
		Map<String, Object> params = buildParam(tableSql, packageName, isJoin, false);
		result.put("mybatis", freemarkerTool.processString("code-generator/mybatis/mybatis.ftl", params));

		//不需要del_flag 等字段
		Map<String, Object> lessFieldMapParam = buildParam(tableSql, packageName, isJoin, true);
		result.put("service", freemarkerTool.processString("code-generator/mybatis/service.ftl", lessFieldMapParam));
		String template = "code-generator/mybatis/controller.ftl";
		if (StringUtils.equalsIgnoreCase(hasApi,"NO")) {
			template = "code-generator/mybatis/controller-no-api.ftl";
		}
		result.put("controller", freemarkerTool.processString(template, lessFieldMapParam));
		result.put("converter", freemarkerTool.processString("code-generator/mybatis/converter.ftl", lessFieldMapParam));
		result.put("mapper", freemarkerTool.processString("code-generator/mybatis/mapper.ftl", lessFieldMapParam));
		result.put("model", freemarkerTool.processString("code-generator/mybatis/model.ftl", lessFieldMapParam));
		result.put("vo", freemarkerTool.processString("code-generator/mybatis/vo.ftl", lessFieldMapParam));
		result.put("api", freemarkerTool.processString("code-generator/mybatis/api.ftl", lessFieldMapParam));
		result.put("listVo", freemarkerTool.processString("code-generator/mybatis/listVo.ftl", lessFieldMapParam));
		return result;
	}


	private Map<String, Object> buildParam(String tableSql, String packageName, String isJoin,Boolean removeUselessField) throws Exception {
		if (StringUtils.isBlank(isJoin)) {
			isJoin = "NO";
		}
		if (StringUtils.isBlank(packageName)) {
			packageName = "com.wdit.modules.xxxx";
		}
		// parse table
		ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql);
		if (removeUselessField) {
			removeUselessField(classInfo.getFieldList());
		}
		// code genarete
		Map<String, Object> params = new HashMap();
		params.put("classInfo", classInfo);
		params.put("packageName", packageName);
		params.put("isJoin", isJoin);
		return params;
	}

	private Map<String, String> fileNamesMap(String className) {
		Map<String, String> map = new HashMap<>();
		map.put("controller", className + "Controller.java");
		map.put("service", className + "Service.java");
		map.put("converter", className + "Converter.java");
		map.put("mapper", className + "Dao.java");
		map.put("mybatis", className + "Dao.xml");
		map.put("api", className + "Api.java");
		map.put("vo", className + "Vo.java");
		map.put("listVo", className + "ListVo.java");
		return map;

	}

	/**
	 * 除mybatis-外这些字段都不需要 会造成不必要的麻烦
	 */
	private void removeUselessField(List<FieldInfo> list) {
		List<String> useLessList = Arrays.asList("del_flag", "update_date", "create_date", "remarks", "priority");
		list.removeIf(item -> useLessList.contains(item.getColumnName()));
	}

}