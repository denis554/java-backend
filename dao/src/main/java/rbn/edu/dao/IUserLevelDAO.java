package rbn.edu.dao;

import rbn.edu.model.UserLevel;

public interface IUserLevelDAO extends IGenericDAO<UserLevel> {

    void removeAllByUserId(long userId);

}
