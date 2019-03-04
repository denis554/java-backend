package rbn.edu.security;

import java.io.IOException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;

import rbn.edu.annotations.PermitAll;
import rbn.edu.filters.CORSFilter;
import rbn.edu.filters.JWTAuthenticationFilter;
import rbn.edu.filters.JWTLoginFilter;
import rbn.edu.jwt.TokenAuthenticationService;
import rbn.edu.utils.ClassLoaderUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

	http.headers().cacheControl();

	http.csrf().disable() //
		.authorizeRequests().antMatchers("/").permitAll().antMatchers(HttpMethod.POST, "/login").permitAll()
		.anyRequest().authenticated().and() //
		.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
		.addFilterBefore(new JWTLoginFilter(authenticationManager(), tokenAuthenticationService),
			UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService),
			UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
	addIgnoringSecurityToEndpoints(web);
	web.ignoring().antMatchers("/getAllProducts");
	super.configure(web);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(authenticationProvider);
    }

    private void addIgnoringSecurityToEndpoints(WebSecurity web) throws ClassNotFoundException, IOException {
	Class<?>[] classes = ClassLoaderUtils.getClasses("rbn.edu.controllers");
	for (Class<?> clazz : classes) {
	    for (Method method : clazz.getMethods()) {
		PermitAll permitAllAnnotation = method.getAnnotation(PermitAll.class);
		if (permitAllAnnotation != null) {
		    RequestMapping requestMappingClassAnnotation = clazz.getAnnotation(RequestMapping.class);
		    RequestMapping requestMappingMethodAnnotation = method.getAnnotation(RequestMapping.class);
		    StringBuilder sb = new StringBuilder();
		    sb.append(requestMappingClassAnnotation.value()[0]);
		    if (requestMappingMethodAnnotation != null & requestMappingMethodAnnotation.value() != null
			    && requestMappingMethodAnnotation.value().length > 0) {
			sb.append(requestMappingMethodAnnotation.value());
		    }
		    web.ignoring().antMatchers(HttpMethod.resolve(requestMappingMethodAnnotation.method()[0].name()),
			    sb.toString());
		}
	    }
	}
    }

}
