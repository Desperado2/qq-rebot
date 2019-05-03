package com.jack.qqrebot.service.dashang;

import org.springframework.stereotype.Service;

/**
 * @Auther: mujj
 * @Date: 2019/5/3 16:31
 * @Description:
 * @Version: 1.0
 */
@Service("dashangService")
public class DashangServiceImpl implements DashangService {
    @Override
    public String getUlr() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("众筹卖女装项目").append("\n\n");
        stringBuffer.append("支付宝收款:").append("\n").append("[CQ:image,file=zfb.png]").append("\n");
        stringBuffer.append("微信收款:").append("\n").append("[CQ:image,file=wx.png]").append("\n");
        stringBuffer.append("QQ收款:").append("\n").append("[CQ:image,file=qq.png]").append("\n\n");
        stringBuffer.append("支付请备注自己qq号哦");
        return stringBuffer.toString();
    }
}
