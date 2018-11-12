package com.app.modules.google;

import com.app.modules.common.bean.ReturnMsg;
import com.app.modules.common.controller.BaseController;
import com.app.modules.common.utils.HtmlTemplateUtil;
import com.app.modules.google.bean.ShareInfoBean;
import com.app.modules.notify.ding.controller.DingtalkService;
import com.app.modules.notify.mail.OkayApiMailService;
import com.google.appengine.api.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("cron")
public class GoogleCronController extends BaseController {
	// 通知多个用分号隔开
	private String notifyToMail = "420671626@qq.com";


    @Autowired
    private OkayApiMailService okMailService;
    @Autowired
	DingtalkService dingtalkService;
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();



	@RequestMapping("dailyShareInfo")
    @ResponseBody
    public ReturnMsg sendMail() {
        try {
            int weekValue = LocalDate.now(ZoneId.of("Asia/Shanghai")).getDayOfWeek().getValue();
            // 不能用Calendar.saturday Calendar.sunday 因为Calendar类中sunday的值为1
            if (weekValue == 6 || weekValue == 7) {
                //周六 周日 不发送周报
                return new ReturnMsg(ReturnMsg.Response.APP_OK);
            }
            Map<String, Object> mailShareInfo = okMailService.getMailShareInfo();
            String mailHtml = HtmlTemplateUtil.render("notify/mail.html", mailShareInfo);

			dingtalkService.sendTextMessageWithAtAll(okMailService.getDingShareInfo(mailShareInfo).toString());
			/*
			* 1、多邮件用英文;进行分割
			* 2、先发钉 再发邮件 钉的api比较稳定 邮件api不太稳定
			*/
            okMailService.sendMail(notifyToMail, (String) mailShareInfo.get("title"), mailHtml);
        } catch (Exception e) {
        	logger.severe("发送通知消息出现严重错误！无法进行正常发送!");
            e.printStackTrace();
            return new ReturnMsg(ReturnMsg.Response.APP_NG);
        }
        return new ReturnMsg(ReturnMsg.Response.APP_OK);
    }


	// 买入点位预警
	@RequestMapping("buyPointWarning")
	@ResponseBody
	public ReturnMsg buyPointWarning() {

		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(100);
		Query q = new Query("WarningPoint");
		PreparedQuery pq = datastore.prepare(q);

		QueryResultList<Entity> results;
		results = pq.asQueryResultList(fetchOptions);
		Entity entity = results.get(0);
		Double CYB_MIN  = ((Long) entity.getProperty("CYB_MIN")).doubleValue();
		Double CYB_MAX  = ((Long) entity.getProperty("CYB_MAX")).doubleValue();
		Double SZ_MIN  = ((Long) entity.getProperty("SZ_MIN")).doubleValue();
		Double SZ_MAX  = ((Long) entity.getProperty("SZ_MAX")).doubleValue();
		Boolean IS_CLOSE = (Boolean) entity.getProperty("IS_CLOSE");
		if (IS_CLOSE) {
			// 关闭通知功能
			return new ReturnMsg(ReturnMsg.Response.APP_OK,CYB_MIN+"|"+CYB_MAX+"|"+SZ_MIN+"|"+SZ_MAX+"|"+IS_CLOSE);
		}
		try {
			int weekValue = LocalDate.now(ZoneId.of("Asia/Shanghai")).getDayOfWeek().getValue();
			if (weekValue == 6 || weekValue == 7) {
				//周六 周日 无需发送
				return new ReturnMsg(ReturnMsg.Response.APP_OK);
			}

			List<Map<String, String>> rawShareInfo = okMailService.getRawShareInfo();
			for (Map<String, String> map : rawShareInfo) {
				String code = map.get("code").replace("\n","");
				if(ShareInfoBean.SHANGZHEGN.equals(code)){
					// 获取上证
					Double cls =Double.parseDouble(map.get("close")) ;
					if (SZ_MIN <= cls  && cls <= SZ_MAX ) {
						dingtalkService.sendTextMessageWithAtAll("上证已到达预设买入点位【"+SZ_MIN+","+SZ_MAX+"】");
						okMailService.sendMail(notifyToMail,"上证买入告警","上证已到达预设买入点位【"+SZ_MIN+","+SZ_MAX+"】");
					}
				}else if(ShareInfoBean.CYB.equals(code)){
					// 获取创业板
					Double cls =Double.parseDouble(map.get("close")) ;
					if (CYB_MIN <= cls  && cls <= CYB_MAX ) {
						dingtalkService.sendTextMessageWithAtAll("创业板已到达预设买入点位【"+CYB_MIN+","+CYB_MAX+"】");
						okMailService.sendMail(notifyToMail,"创业板买入告警","创业板已到达预设买入点位【"+CYB_MIN+","+CYB_MAX+"】");
					}
				}
			}
		} catch (Exception e) {
			logger.severe("发送通知消息出现严重错误！无法进行正常发送!");
			e.printStackTrace();
		}
		return new ReturnMsg(ReturnMsg.Response.APP_OK);
	}

}
