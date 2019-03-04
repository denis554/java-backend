package rbn.edu.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import rbn.edu.dao.IUserLevelDAO;
import rbn.edu.model.UserLevel;
import rbn.edu.model.api.FilterDTO;

@Repository
@Transactional
public class UserLevelDAO extends GenericDAO<UserLevel> implements IUserLevelDAO {

    @Override
    protected Class<UserLevel> getPersistenceClass() {
	return UserLevel.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeAllByUserId(long userId) {
	Criteria criteria = getSession().createCriteria(getPersistenceClass());
	criteria.createAlias("user", "user");
	criteria.add(Restrictions.eq("user.id", userId));
	List<UserLevel> levels = criteria.list();
	if (!CollectionUtils.isEmpty(levels)) {
	    for (UserLevel userLevel : levels) {
		userLevel.setUser(null);
		getSession().delete(userLevel);
	    }
	}
    }

    public List<UserLevel> getAll(FilterDTO<?> filter) {
	return this.getAll();
    }

}
