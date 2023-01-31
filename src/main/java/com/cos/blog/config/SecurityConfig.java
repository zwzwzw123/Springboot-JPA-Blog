package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;

//Bean 등록 : 스프링 컨테이너에서 객체를 관리 할 수 있게 하는 것
@Configuration //Bean등록(IoC관리)
@EnableWebSecurity //시큐리티 필터가 등록이 됨
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean //IoC가 됨 -> return하는 값을 스프링이 관리
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//같은 해쉬 암호화해서 db에 있는 해쉬랑 비교 가능
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf 토큰 비활성화(테스트 시 필요)
			.authorizeHttpRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") 
				.defaultSuccessUrl("/");//스프링 시큐리티가 해당주소로 요청이 오는 로그인을 가로채서 대신 로그인해줌
	}

}
