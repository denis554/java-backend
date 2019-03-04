package rbn.edu.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import rbn.edu.enums.UserAuthorizationType;

@Entity
@Table(name = "USL_USER_LEVEL")
@AttributeOverrides({ @AttributeOverride(name = AbstractEntity.PK, column = @Column(name = UserLevel.PK)) })
public class UserLevel extends AbstractEntity<Long> implements GrantedAuthority {

    public static final String USN_LEVEL = "USL_LEVEL";
    public static final String PK = "USL_ID";
    private static final long serialVersionUID = -4600840153957593563L;

    @ManyToOne
    @JoinColumn(name = User.PK, nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = USN_LEVEL, nullable = false, length = 20)
    private UserAuthorizationType level;

    public UserLevel(UserAuthorizationType level, User usuario) {
	this.level = level;
	this.user = usuario;
    }

    public UserLevel() {
    }

    public String getAuthority() {
	return level.toString();
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public UserAuthorizationType getLevel() {
	return level;
    }

    public void setLevel(UserAuthorizationType level) {
	this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
	UserLevel userLevel = (UserLevel) obj;
	if (this.id != null && userLevel.getId() != null) {
	    if (this.id.intValue() == userLevel.getId().intValue()) {
		return true;
	    }

	}
	return false;
    }

}