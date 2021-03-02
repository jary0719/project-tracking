package net.suncaper.projecttracking.controller;

import net.suncaper.projecttracking.client.UserClient;
import net.suncaper.projecttracking.domain.User;
import net.suncaper.projecttracking.manager.SMSManager;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class HomeController {
    @Autowired
    private UserClient helloClient;
    @Autowired
    private SMSManager smsManager;

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("sms/login")
    public String smsLoginPage() {
        return "sms-login";
    }

    @GetMapping("403")
    public String page403(Model model) {
        model.addAttribute("page", "include/error/403");

        return "index";
    }

    @GetMapping("dashboard")
    public String dashboardPage(Model model) {
        model.addAttribute("page", "dashboard");
        return "index";
    }

    @GetMapping("/sms/login-code")
    @ResponseBody
    public boolean getSMSLoginCode(@RequestParam("mobile") String mobile, HttpSession session) {
        smsManager.sendSmsLoginMessage(mobile, session);
        return Boolean.TRUE;
    }
}
