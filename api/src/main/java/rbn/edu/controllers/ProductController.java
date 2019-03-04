package rbn.edu.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rbn.edu.annotations.PermitAll;
import rbn.edu.config.exceptions.BusinessException;
import rbn.edu.enums.ProductTypeEnum;
import rbn.edu.model.Product;
import rbn.edu.model.api.FilterDTO;
import rbn.edu.model.api.ResponseServer;
import rbn.edu.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public void send(@RequestBody Product product) throws BusinessException {
	logger.info("POST, product {} send", product.getName());
	productService.add(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody Product product) throws BusinessException {
	logger.info("PUT, product {} update", product.getName());
	productService.update(product);
    }

    @PermitAll
    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VISITOR')")
    @RequestMapping(method = RequestMethod.GET)
    public List<Product> get() {
	logger.info("GET, product get");
	return productService.getAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VISITOR', 'ROLE_CUSTOMER')")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseServer<Product> getByQuery(@RequestBody FilterDTO<Product> dto) throws BusinessException {
	logger.info("POST, product getByQuery");
	return productService.getAll(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VISITOR')")
    @RequestMapping(value = "/productTypes", method = RequestMethod.GET)
    public List<ProductTypeEnum> getProductTypes() {
	logger.info("GET, product getProductTypes");
	return productService.getAllProductTypes();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws BusinessException {
	logger.info("DELETE, productId {} delete", id);
	productService.remove(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getById(@PathVariable("id") long id) {
	logger.info("GET, productId {} getById", id);
	return productService.getById(id);
    }

}
