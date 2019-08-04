package com.freenow.config;

import com.freenow.security.DummyPasswordEncoder;
import com.freenow.security.enums.AuthorityTypes;
import com.freenow.security.jwt.JwtSecurityConfig;
import com.freenow.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private static final String[] AUTH_WHITELIST = {
        "/v2/api-docs",
        "/configuration/ui",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/security",
        "/swagger-ui.html",
        "/h2",
        "/h2/**",
        "/webjars/**"};
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new DummyPasswordEncoder();
    }


    @Bean
    @Override public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }


    @Override protected void configure(HttpSecurity http) throws Exception
    {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(AUTH_WHITELIST).permitAll()
            .antMatchers("/auth/login").permitAll()
            .antMatchers(HttpMethod.GET).hasAuthority(AuthorityTypes.USER.toString())
            .antMatchers(HttpMethod.POST).hasAuthority(AuthorityTypes.USER.toString())
            .antMatchers(HttpMethod.PUT).hasAuthority(AuthorityTypes.ADMIN.toString())
            .antMatchers(HttpMethod.DELETE).hasAuthority(AuthorityTypes.ADMIN.toString())
            .anyRequest().authenticated()
            .and()
            .apply(new JwtSecurityConfig(jwtTokenProvider));
    }


    @Override public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }
}
