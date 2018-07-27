package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
        /*auth.inMemoryAuthentication()
          .withUser("berk").password(passwordEncoder().encode("1234")).roles("USER");*/
    	auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("SELECT user_email, user_password, 1 FROM `user` WHERE user_email=?")
		.authoritiesByUsernameQuery("select user_email, user_role from user WHERE user_email = ?")
		.passwordEncoder(passwordEncoder());
          
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
////    	.antMatchers("/admin/**").access("hasRole('ADMIN')")
//    	
//        http
//        .authorizeRequests()
//        .antMatchers("/login**").permitAll()
//        .antMatchers("/admin**").hasRole("ADMIN")
//        .antMatchers("/admin/**").hasRole("ADMIN")
//        .anyRequest().authenticated()
//        .and()
////        
////          .authorizeRequests()
////          .anyRequest().permitAll()
////          .and()
//
//          .formLogin()
//          .loginPage("/login")
//          .defaultSuccessUrl("/books")
//          .failureUrl("/login?error")
//          .and()
//          .logout().logoutSuccessUrl("/login").and().csrf().disable();
        
    	http
        .cors().and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().and()
        .authorizeRequests()
        .antMatchers("**").permitAll()
        .and()
        .csrf().disable();
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurerAdapter() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST", "GET", "PUT",
              "DELETE");
        }
      };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}