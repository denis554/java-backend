package rbn.edu.service.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import rbn.edu.enums.ProductTypeEnum;
import rbn.edu.model.Product;
import rbn.edu.model.api.FilterDTO;
import rbn.edu.model.api.ResponseServer;
import rbn.edu.service.IProductService;

@Service
public class ProductService implements IProductService {

    private static List<Product> products = new ArrayList<Product>();
    private static long sequence = 13;

    public ProductService() {
	products.add(new Product((long) 1, "Product1", new BigDecimal(11), ProductTypeEnum.CAR));
	products.add(new Product((long) 2, "Product2", new BigDecimal(22), ProductTypeEnum.UTILITY));
	products.add(new Product((long) 3, "Product3", new BigDecimal(31), ProductTypeEnum.CLOTH));
	products.add(new Product((long) 4, "Product4", new BigDecimal(83), ProductTypeEnum.CAR));
	products.add(new Product((long) 5, "Product5", new BigDecimal(44), ProductTypeEnum.CLOTH));
	products.add(new Product((long) 6, "Product6", new BigDecimal(29), ProductTypeEnum.CAR));
	products.add(new Product((long) 7, "Product7", new BigDecimal(31), ProductTypeEnum.UTILITY));
	products.add(new Product((long) 8, "Product8", new BigDecimal(23), ProductTypeEnum.CLOTH));
	products.add(new Product((long) 9, "Product9", new BigDecimal(13), ProductTypeEnum.UTILITY));
	products.add(new Product((long) 10, "Product10", new BigDecimal(53), ProductTypeEnum.CLOTH));
	products.add(new Product((long) 11, "Product11", new BigDecimal(82), ProductTypeEnum.CAR));
	products.add(new Product((long) 12, "Product12", new BigDecimal(63), ProductTypeEnum.UTILITY));
    }

    @Override
    public void add(Product product) {
	product.setId(sequence);
	products.add(product);
	sequence++;
    }

    @Override
    public void update(Product product) {
	Product productToUpdate = getProductById(product.getId());
	products.remove(productToUpdate);
	products.add(product);
    }

    @Override
    public List<Product> getAll() {
	return products;
    }

    @Override
    public Product getById(long id) {
	return getProductById(id);
    }

    @Override
    public void remove(long id) {
	products.remove(getProductById(id));
    }

    private Product getProductById(final long id) {
	return products.stream().filter(p -> p.getId() == id).collect(Collectors.toList()).get(0);
    }

    @Override
    public List<ProductTypeEnum> getAllProductTypes() {
	List<ProductTypeEnum> list = new ArrayList<ProductTypeEnum>();
	for (ProductTypeEnum type : ProductTypeEnum.values()) {
	    list.add(type);
	}
	return list;
    }

    @Override
    public ResponseServer<Product> getAll(FilterDTO<Product> dto) {
	List<Product> list = getAll();
	if (dto.getOrderProperty() != null) {
	    Comparator comparator = null;
	    if (dto.getOrderProperty().equals("id")) {
		comparator = Comparator.comparing(Product::getId);
	    }
	    if (dto.getOrderProperty().equals("name")) {
		comparator = Comparator.comparing(Product::getName);
	    }
	    if (dto.getOrderProperty().equals("value")) {
		comparator = Comparator.comparing(Product::getValue);
	    }
	    if (dto.getOrderProperty().equals("productType")) {
		comparator = Comparator.comparing(Product::getProductType);
	    }
	    if (dto.getOrderProperty().equals("date")) {
		comparator = Comparator.comparing(Product::getDate, Comparator.nullsLast(Comparator.naturalOrder()));
	    }
	    if (!dto.isOrderAsc()) {
		comparator = comparator.reversed();
	    }
	    if (comparator != null) {
		list.sort(comparator);
	    }
	}
	int page = 1;
	if (dto.getOffset() <= 1) {
	    page = 0;
	} else {
	    page = dto.getOffset();
	}
	List<Product> newList = list.stream().skip(page).limit(dto.getPageSize()).collect(Collectors.toList());
	return new ResponseServer<Product>(list.size(), newList);
    }

}
