package com.vn.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vn.auth.jwt.AuthEntryPointJwt;
import com.vn.auth.jwt.AuthTokenFilter;
import com.vn.auth.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and().authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/api/auth/signin").permitAll()
				.antMatchers("/static/frontend/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/upload/**").permitAll()
				.antMatchers("/frontend/**").permitAll()
				
				
				.antMatchers("/", "/home").permitAll()
				
				.antMatchers("/about").permitAll()
				.antMatchers("/about").permitAll()
				.antMatchers("/contact").permitAll()
				.antMatchers( "/product/**").permitAll()
				.antMatchers("/403").permitAll()
				.antMatchers("/token").permitAll()
				.antMatchers("/backend/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated().and().formLogin()
				.loginPage("/login").loginProcessingUrl("/login")
				.defaultSuccessUrl("/admin.html", true).permitAll()
				.and().logout().permitAll();
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**", "/static/**", "/admin/css/**", "/admin/js/**", "/admin/images/**",
//				"/admin/img/**", "/admin/lib/**", "/admin/fonts/**", "/admin/vendor/**");
//	}

}
