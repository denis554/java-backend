package rbn.edu.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import rbn.edu.config.ProjectConstants;
import rbn.edu.config.exceptions.BusinessException;
import rbn.edu.dao.IUserDAO;
import rbn.edu.dao.IUserLevelDAO;
import rbn.edu.model.User;
import rbn.edu.model.UserLevel;
import rbn.edu.model.api.FilterDTO;
import rbn.edu.service.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    private Environment env;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IUserLevelDAO userLevelDAO;

    @Override
    public User getUserLogged() {
	String username = SecurityContextHolder.getContext().getAuthentication().getName();
	return userDAO.findUserByLogin(username);
    }

    @Override
    @Transactional
    public void add(User t) throws BusinessException {
	try {
	    User userFromDB = userDAO.findUserByLogin(t.getLogin());
	    if (userFromDB != null) {
		throw new BusinessException(env.getProperty(ProjectConstants.USER_ALREADY_EXISTS));
	    }
	    t.setPassword(new BCryptPasswordEncoder().encode(t.getPassword()));
	    Set<UserLevel> userLevels = t.getUserLevels();
	    userDAO.add(t);
	    for (UserLevel level : userLevels) {
		userLevelDAO.add(level);
	    }

	} catch (Exception e) {
	    throw new BusinessException(e.getMessage());
	}
    }

    @Override
    @Transactional
    public void update(User t) throws BusinessException {
	try {
	    User userFromDB = userDAO.findUserByLogin(t.getLogin());
	    if (userFromDB != null && userFromDB.getId().intValue() != t.getId().intValue()) {
		throw new BusinessException(env.getProperty(ProjectConstants.USER_ALREADY_EXISTS));
	    }
	    t.setPassword(new BCryptPasswordEncoder().encode(t.getPassword()));
	    removeAllUserLevelsByUserId(t.getId());
	    Set<UserLevel> userLevels = t.getUserLevels();
	    updateTransactional(t);
	    for (UserLevel level : userLevels) {
		userLevelDAO.add(level);
	    }
	} catch (Exception e) {
	    throw new BusinessException(e.getMessage());
	}
    }

    @Transactional
    private void updateTransactional(User t) {
	userDAO.update(t);
    }

    @Transactional
    private void removeAllUserLevelsByUserId(long id) {
	userLevelDAO.removeAllByUserId(id);
    }

    @Override
    @Transactional
    public List<User> getAll() {
	return userDAO.getAll();
    }

    @Override
    @Transactional
    public User getById(long id) {
	return userDAO.getById(id);
    }

    @Override
    @Transactional
    public void remove(long id) throws BusinessException {
	try {
	    removeAllUserLevelsByUserId(id);
	    userDAO.remove(id);
	} catch (Exception e) {
	    throw new BusinessException(e.getMessage());
	}
    }

    @Override
    public UserLevel getUserLevelById(long id) {
	return userLevelDAO.getById(id);
    }

    @Override
    @Transactional
    public User getUserByLogin(String login) {
	return userDAO.findUserByLogin(login);
    }

    public List<User> getAll(FilterDTO<?> dto) {
	return this.getAll();
    }

}
