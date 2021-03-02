package net.suncaper.projecttracking.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    //进行授权，将未授权的token转化为授权后的token
    // 1. 如果是UserNameAndPassword：从数据库读取用户信息、进行信息比对，如果成功，返回授权成功的token，如果失败，抛出异常
    // 2. 如果是短信登录
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //1. 获取表单的手机号和验证码
        String mobile = (String) authentication.getPrincipal();

        //2. 和session里面的手机号和验证码进行比对
        checkSms(mobile);

        //3. 如果比对正确，返回一个授权后的token
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        SmsCodeAuthenticationToken authResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        authResult.setDetails(userDetails);

        //4. 如果失败，抛出“验证码”错误的异常
        return authResult;
    }

    public void checkSms(String mobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputSmsCode = request.getParameter("smsCode");
        Map smsCodeMap = (Map) request.getSession().getAttribute("SMS_CODE_MAP");
        if (smsCodeMap == null) {
            throw new BadCredentialsException("未检测到申请验证码");
        }
        String smsCode = (String) smsCodeMap.get(mobile);

        if (!StringUtils.equals(inputSmsCode, smsCode)) {
            throw new BadCredentialsException("验证码不匹配");
        }
    }

    @Override
    //判定当前传递的token是否能被当前provider处理
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
