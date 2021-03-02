package net.suncaper.projecttracking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class CustomWebSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Qualifier("customUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SmsAuthenticationSuccessHandler smsAuthenticationSuccessHandler;
    @Autowired
    private SmsAuthenticationFailureHandler smsAuthenticationFailureHandler;


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/adminlte/**", "/bootstrap-table/**", "/jquery/**", "/jquery-validation/**", "/layer/**", "/chartjs/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sms/login").permitAll()
                .antMatchers("/sms/login-code").permitAll()
                .antMatchers("/test").permitAll()
                .antMatchers("/page1").hasAuthority("PAGE_1")
                .antMatchers("/page2").hasAuthority("PAGE_2")
                .antMatchers("/page3").hasAuthority("PAGE_3")
                .anyRequest().fullyAuthenticated();

        http.formLogin().loginPage("/login").defaultSuccessUrl("/user").failureUrl("/login?error").permitAll();
        http.csrf().disable();
        http.exceptionHandling().accessDeniedPage("/403");

        http.authenticationProvider(createSmsProvider())
                .addFilterAfter(createSmsFilter(), UsernamePasswordAuthenticationFilter.class);

//        http.rememberMe();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        auth.eraseCredentials(false);
    }

    public SmsCodeAuthenticationFilter createSmsFilter() throws Exception {
        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(smsAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(smsAuthenticationFailureHandler);

        return filter;
    }

    public SmsCodeAuthenticationProvider createSmsProvider() {
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
