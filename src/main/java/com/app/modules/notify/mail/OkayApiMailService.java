package com.app.modules.notify.mail;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.IntFunction;

@Service
public class OkayApiMailService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${email.url}")
	private String emailUrl;

	@Value("${email.key}")
	private String emailKey;

	@Value("${share.info.url}")
	private String shareUrl;

	public void sendMail(String to, String title, String content) {
		MultiValueMap map = new LinkedMultiValueMap();
		map.add("s", "App.Email.Send");
		map.add("app_key", emailKey);
		map.add("address", to);
		map.add("title", title);
		map.add("content", content);
		String s = restTemplate.postForObject(emailUrl, map, String.class);
		JSONObject jsonObject = JSONObject.parseObject(s);
	}

	public List<Map<String, String>> getRawShareInfo() {
		// List 返回空代表数据异常
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String str = restTemplate.getForObject(shareUrl, String.class);
			JSONObject info = JSONObject.parseObject(str);
			if(info.getString("error_code").equals("0")){
				// 数据ok
				JSONArray data = info.getJSONArray("data");
				for(int i=0; i < data.size();i++){
					Map<String,String> item = new HashMap<>();
					JSONObject o = (JSONObject) data.get(i);
					item.put("open",String.format("%.2f",Double.parseDouble(o.getString("open"))));
					item.put("close",String.format("%.2f",Double.parseDouble(o.getString("close"))));
					item.put("change",o.getString("change"));
					item.put("name",o.getString("name"));
					item.put("code",o.getString("code"));
					item.put("amount",o.getString("amount"));
					item.put("date",o.getString("date"));
					list.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public Map<String,Object> getMailShareInfo(){
		Map<String,Object> rtn = new HashMap<>();
		//需要的指数: 上证指数、创业板指、沪深300、深证指数、上证180、中证500
		List<String> need = Arrays.asList("sh000001","sz399006","sh000300","sz399106",
				"sh000010","sh000905");
		List<Map<String,String>> list = new ArrayList<>();
		// init list
		for(int i = 0; i<=5;i++){
			list.add(null);
		}
		List<Map<String,String >> shareInfo = getRawShareInfo();
		for (Map map : shareInfo) {
			String code = (String)map.get("code");
			code = code.replace("\n", "");
			if(code.equals("sh000001")){
				list.add(0,map);
			}else if (code.equals("sz399006")){
				list.add(1,map);
			}else if (code.equals("sh000300")){
				list.add(2,map);
			}else if (code.equals("sz399106")){
				list.add(3,map);
			}else if (code.equals("sh000010")){
				list.add(4,map);
			}else if (code.equals("sh000905")){
				list.add(5,map);
			}
		}
		System.out.println(list.get(0).get("date"));
		String date = (String) list.get(0).get("date");
		date = date.replaceFirst("-", "年");
		date = date.replaceFirst("-", "月");
		String panStr = LocalDateTime.now(ZoneId.of("Asia/Shanghai")).getHour()<13?"午盘":"尾盘";
		rtn.put("dateStr",date);
		rtn.put("panStr",panStr);
		list.removeIf(Objects::isNull);
		rtn.put("shareList",list);
		rtn.put("title",date+"简报["+panStr+"]");
		return rtn;
	}


	public Map<String,Object> getDingShareInfo(Map<String,Object> mailInfo ){
		// 钉钉只发送简略信息 只发送上证指数 和 创业板指数
		List<Map<String,String >> shareList = (List<Map<String, String>>) mailInfo.get("shareList");
		Map<String, Object> data = new HashMap<>();
		data.put("date",mailInfo.get("dateStr"));

		for (Map map:shareList){
			String code = ((String) map.get("code")).replaceAll("\\s","");
			if (code.equals("sh000001")){
				// 上证指数
				data.put("上证指数","收:"+map.get("close")+"  涨幅:"+map.get("change"));
			}else if(code.equals("sz399006")){
				data.put("创业板指","收:"+map.get("close")+"  涨幅:"+map.get("change"));
			}
		}
		return data;
	}
}
