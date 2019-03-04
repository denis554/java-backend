package rbn.edu.dao;

import rbn.edu.model.User;

public interface IUserDAO extends IGenericDAO<User> {

    User findUserByLogin(String login);

}
