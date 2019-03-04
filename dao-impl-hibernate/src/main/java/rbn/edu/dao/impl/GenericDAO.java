package rbn.edu.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import rbn.edu.dao.IGenericDAO;

public abstract class GenericDAO<T> implements IGenericDAO<T> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
	Session session = null;
	try {
	    session = sessionFactory.getCurrentSession();
	} catch (HibernateException e) {
	    session = sessionFactory.openSession();
	}
	return session;
    }

    @Override
    public T add(T obj) {
	getSession().save(obj);
	getSession().refresh(obj);
	return obj;
    }

    @Override
    public T update(T obj) {
	getSession().update(obj);
	return obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
	Criteria criteria = getSession().createCriteria(getPersistenceClass());
	return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getById(long id) {
	Criteria criteria = getSession().createCriteria(getPersistenceClass());
	criteria.add(Restrictions.eq("id", id));
	return (T) criteria.uniqueResult();
    }

    @Override
    public void remove(long id) {
	Criteria criteria = getSession().createCriteria(getPersistenceClass());
	criteria.add(Restrictions.eq("id", id));
	@SuppressWarnings("unchecked")
	T prod = (T) criteria.uniqueResult();
	getSession().delete(prod);
    }

    protected abstract Class<T> getPersistenceClass();
}
