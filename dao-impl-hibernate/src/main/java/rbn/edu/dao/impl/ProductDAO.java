package rbn.edu.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rbn.edu.dao.IProductDAO;
import rbn.edu.model.Product;
import rbn.edu.model.api.FilterDTO;
import rbn.edu.model.api.ResponseServer;

@Repository
@Transactional
public class ProductDAO extends GenericDAO<Product> implements IProductDAO {

    @Override
    protected Class<Product> getPersistenceClass() {
	return Product.class;
    }

    @SuppressWarnings("unchecked")
    public ResponseServer<Product> getAll(FilterDTO<Product> filter) {
	Criteria criteria = getSession().createCriteria(getPersistenceClass());
	criteria.setMaxResults(filter.getPageSize());
	criteria.setFirstResult(filter.getOffset());
	if (filter.getOrderProperty() != null) {
	    if (filter.isOrderAsc()) {
		criteria.addOrder(Order.asc(filter.getOrderProperty()));
	    } else {
		criteria.addOrder(Order.desc(filter.getOrderProperty()));
	    }
	}
	criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

	List<Product> products = criteria.list();
	Criteria criteriaCount = getSession().createCriteria(getPersistenceClass());
	criteriaCount.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	int size = ((Number) criteriaCount.setProjection(Projections.rowCount()).uniqueResult()).intValue();

	return new ResponseServer<Product>(size, products);
    }

    public Product getByName(String name) {
	Criteria criteria = getSession().createCriteria(getPersistenceClass());
	criteria.add(Restrictions.eq("name", name));
	return (Product) criteria.uniqueResult();
    }

}
