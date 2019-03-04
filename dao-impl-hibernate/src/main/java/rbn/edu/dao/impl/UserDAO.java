package rbn.edu.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import rbn.edu.dao.IUserDAO;
import rbn.edu.model.User;
import rbn.edu.model.api.FilterDTO;

@Repository
public class UserDAO extends GenericDAO<User> implements IUserDAO {

    @Override
    protected Class<User> getPersistenceClass() {
	return User.class;
    }

    public User findUserByLogin(String login) {
	Criteria criteria = getSession().createCriteria(getPersistenceClass());
	criteria.add(Restrictions.eq("login", login));
	return (User) criteria.uniqueResult();
    }

    public List<User> getAll(FilterDTO<?> filter) {
	return this.getAll();
    }

}
