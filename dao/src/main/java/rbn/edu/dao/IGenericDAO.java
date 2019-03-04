package rbn.edu.dao;

import java.util.List;

public interface IGenericDAO<T> {

    T add(T obj);

    T update(T obj);

    List<T> getAll();

    T getById(long id);

    void remove(long id);

}
