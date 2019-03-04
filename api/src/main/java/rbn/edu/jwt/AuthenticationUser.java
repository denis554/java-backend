package rbn.edu.jwt;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import rbn.edu.model.UserLevel;

public class AuthenticationUser implements Authentication {

    private static final long serialVersionUID = -4202601568054467013L;
    private boolean authenticated;
    private String login;
    private String password;
    private boolean enabled;
    private List<UserLevel> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return permissions;
    }

    public void setAuthorities(List<UserLevel> userLevels) {
	permissions = userLevels;
    }

    @Override
    public Object getCredentials() {
	return password;
    }

    @Override
    public Object getDetails() {
	return null;
    }

    @Override
    public Object getPrincipal() {
	return this;
    }

    @Override
    public boolean isAuthenticated() {
	return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
	this.authenticated = b;
    }

    @Override
    public String getName() {
	return login;
    }

    public boolean isEnabled() {
	return enabled;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public void setEnabled(boolean enabled) {
	this.enabled = enabled;
    }

}
