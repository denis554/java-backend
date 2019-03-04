package rbn.edu.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import rbn.edu.dao.IUserProductDAO;
import rbn.edu.model.UserProduct;

@Repository
public class UserProductDAO extends GenericDAO<UserProduct> implements IUserProductDAO {

    private static final long serialVersionUID = 7317722483218052949L;

    @Override
    protected Class<UserProduct> getPersistenceClass() {
	return UserProduct.class;
    }

    @Override
    public void save(List<UserProduct> list) {
	list.forEach(item -> add(item));
    }

    @Override
    public void clear(long userId) {
	List<UserProduct> list = getByUserId(userId);
	if (!CollectionUtils.isEmpty(list)) {
	    list.forEach(item -> remove(item.getId()));
	}

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserProduct> getByUserId(long userId) {
	Criteria criteria = getSession().createCriteria(UserProduct.class);
	criteria.createAlias("user", "user");
	criteria.add(Restrictions.eq("user.id", userId));
	criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserProduct> getByProductId(long productId) {
	Criteria criteria = getSession().createCriteria(UserProduct.class);
	criteria.createAlias("product", "product");
	criteria.add(Restrictions.eq("product.id", productId));
	criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	return criteria.list();
    }

    @Override
    public void removeByProductId(long productId) {
	List<UserProduct> list = getByProductId(productId);
	if (!CollectionUtils.isEmpty(list)) {
	    list.forEach(item -> remove(item.getId()));
	}
    }

}
