package rbn.edu.service.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import rbn.edu.config.exceptions.BusinessException;
import rbn.edu.enums.UserAuthorizationType;
import rbn.edu.model.User;
import rbn.edu.model.UserLevel;
import rbn.edu.service.IUserService;

@Service
public class UserService implements IUserService {

    private static final List<User> users = new ArrayList<User>();

    public UserService() {
	String password = new BCryptPasswordEncoder().encode("adm");
	users.add(new User(1, "adm", password, true, UserAuthorizationType.ROLE_ADMIN));
	users.add(new User(2, "visitor", password, true, UserAuthorizationType.ROLE_VISITOR));
	users.add(new User(3, "customer", password, true, UserAuthorizationType.ROLE_CUSTOMER));
    }

    @Override
    public void add(User t) throws BusinessException {
	users.add(t);
    }

    @Override
    public void update(User t) throws BusinessException {
	users.remove(t);
	users.add(t);
    }

    @Override
    public List<User> getAll() {
	return users;
    }

    @Override
    public User getById(long id) {
	return users.stream().filter(user -> user.getId().intValue() == id).findFirst().get();
    }

    @Override
    public void remove(long id) throws BusinessException {
	User selectedUser = users.stream().filter(user -> user.getId().intValue() == id).findFirst().get();
	users.remove(selectedUser);
    }

    @Override
    public UserLevel getUserLevelById(long id) {
	for (User user : users) {
	    for (UserLevel userLevel : user.getUserLevels()) {
		if (userLevel.getId().intValue() == id) {
		    return userLevel;
		}
	    }
	}
	return null;
    }

    @Override
    public User getUserByLogin(String login) {
	return users.stream().filter(user -> user.getLogin().equals(login)).findFirst().get();
    }

    @Override
    public User getUserLogged() {
	String username = SecurityContextHolder.getContext().getAuthentication().getName();
	return getUserByLogin(username);
    }

}
