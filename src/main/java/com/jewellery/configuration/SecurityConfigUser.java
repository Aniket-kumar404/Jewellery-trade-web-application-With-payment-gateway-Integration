//package com.jewellery.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import com.jewellery.service.CustomUserDetailService;
//
//@EnableWebSecurity
//@Configuration
//@Order(2)
//public class SecurityConfigUser extends WebSecurityConfigurerAdapter{
//	@Autowired
//	CustomUserDetailService customUserDetailService;
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//							.antMatchers("/cart/**", "/payment/**")
//				.authenticated()
//				.and()
//				.formLogin()
//				.loginPage("/login").permitAll()
//				.failureUrl("/login?error=true")
//				.defaultSuccessUrl("/admin")
//				.usernameParameter("email")
//				.passwordParameter("password")
//				.and()
//				.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//				.logoutSuccessUrl("/login")
//				.invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID").and()
//				.exceptionHandling()
//				.accessDeniedPage("/access-denied").and().csrf().disable();
//	}
//
//	@Bean
//	public BCryptPasswordEncoder bCryptEncoder1() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailService);
//	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/css/**", "/js/**");
//	}
//}
