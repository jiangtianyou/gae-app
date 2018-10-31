package com.app.modules.google;

import com.app.modules.common.bean.ReturnMsg;
import com.app.modules.common.utils.HtmlTemplateUtil;
import com.app.modules.notify.ding.controller.DingtalkService;
import com.app.modules.notify.ding.message.SendResult;
import com.app.modules.notify.ding.message.TextMessage;
import com.app.modules.notify.mail.OkayApiMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Map;

@Controller
@RequestMapping("cron")
public class GoogleCronController {
    @Autowired
    private OkayApiMailService okMailService;

    @Autowired
	DingtalkService dingtalkService;

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
            // 多邮件用英文;进行分割
            okMailService.sendMail("420671626@qq.com", (String) mailShareInfo.get("title"), mailHtml);
			dingtalkService.sendTextMessageWithAtAll(okMailService.getDingShareInfo(mailShareInfo).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnMsg(ReturnMsg.Response.APP_NG);
        }
        return new ReturnMsg(ReturnMsg.Response.APP_OK);
    }
}
