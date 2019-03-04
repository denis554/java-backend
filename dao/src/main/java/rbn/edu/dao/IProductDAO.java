package rbn.edu.dao;

import rbn.edu.model.Product;
import rbn.edu.model.api.FilterDTO;
import rbn.edu.model.api.ResponseServer;

public interface IProductDAO extends IGenericDAO<Product> {

    ResponseServer<Product> getAll(FilterDTO<Product> filter);
    
    Product getByName(String name);

}
