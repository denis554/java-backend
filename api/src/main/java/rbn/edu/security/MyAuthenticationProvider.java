package rbn.edu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import rbn.edu.jwt.AuthenticationUser;
import rbn.edu.model.User;
import rbn.edu.service.IUserService;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private IUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	User userLogged = userService.getUserByLogin(authentication.getName());

	if (userLogged == null) {
	    throw new UsernameNotFoundException("User not found");
	}

	String password = (String) authentication.getCredentials();

	boolean passwordMatches = new BCryptPasswordEncoder().matches(password, userLogged.getPassword());

	if (!passwordMatches) {
	    throw new BadCredentialsException("Password don't match");
	}

	if (!userLogged.isEnabled()) {
	    throw new DisabledException("Account disabled");
	}

	return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
	return Authentication.class.isAssignableFrom(AuthenticationUser.class);
    }

}
