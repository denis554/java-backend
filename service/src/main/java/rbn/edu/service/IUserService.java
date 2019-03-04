package rbn.edu.service;

import rbn.edu.model.User;
import rbn.edu.model.UserLevel;

public interface IUserService extends IGenericService<User> {

    UserLevel getUserLevelById(long id);

    User getUserByLogin(String login);

    User getUserLogged();

}
