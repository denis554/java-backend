package rbn.edu.service;

import java.util.List;

import rbn.edu.config.exceptions.BusinessException;

public interface IGenericService<T> {

    void add(T t) throws BusinessException;

    void update(T t) throws BusinessException;

    List<T> getAll();

    T getById(long id);

    void remove(long id) throws BusinessException;

}
