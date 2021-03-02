package net.suncaper.projecttracking.manager;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SMSManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.accessSecret}")
    private String accessSecret;
    @Value("${sms.SignName}")
    private String SignName;

    public void sendSMS(String mobile, String templateCode, String param) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", param);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public String generateSmsCode() {
        String code = StringUtils.leftPad(String.valueOf(new Random().nextInt(10000)), 4, "0");
        return code;
    }

    public void writeSmsCodeToSession(HttpSession session, String mobile, String code) {
        Map smsCodeMap = (Map)session.getAttribute("SMS_CODE_MAP");
        if(smsCodeMap == null) {
            session.setAttribute("SMS_CODE_MAP", new HashMap<>());
        }
        ((Map)session.getAttribute("SMS_CODE_MAP")).put(mobile, code);
    }

    public void sendSmsLoginMessage(String mobile, HttpSession session) {
        String code = generateSmsCode();
        writeSmsCodeToSession(session, mobile, code);
        logger.info("sms code is {}", code);
//        this.sendSMS(mobile, "SMS_178755990", "{\"code\": \"" +  code + "\"}");
    }
}
