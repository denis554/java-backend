package rbn.edu.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import rbn.edu.jwt.TokenAuthenticationService;
import rbn.edu.model.User;
import rbn.edu.utils.AuthenticationUserWrapper;
import rbn.edu.utils.ResponseJsonUtil;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public JWTLoginFilter(AuthenticationManager authenticationManager,
	    TokenAuthenticationService tokenAuthenticationService) {
	super(new AntPathRequestMatcher("/login"));
	this.tokenAuthenticationService = tokenAuthenticationService;
	setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
	    HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
	User user = new ObjectMapper().readValue(httpServletRequest.getInputStream(), User.class);
	return getAuthenticationManager().authenticate(AuthenticationUserWrapper.get().convert(user));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	    Authentication authentication) throws IOException, ServletException {
	String name = authentication.getName();
	tokenAuthenticationService.addAuthentication(response, name);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	    AuthenticationException failed) throws IOException, ServletException {
	ResponseJsonUtil.get().sendResponse(response, HttpStatus.UNAUTHORIZED, failed.getMessage());
    }
}
