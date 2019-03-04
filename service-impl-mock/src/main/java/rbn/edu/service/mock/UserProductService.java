package rbn.edu.service.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rbn.edu.config.exceptions.BusinessException;
import rbn.edu.model.UserProduct;
import rbn.edu.service.IUserProductService;
import rbn.edu.service.IUserService;

@Service
public class UserProductService implements IUserProductService {

    private static final long serialVersionUID = -3859093208710164557L;
    private static final List<UserProduct> usersProducts = new ArrayList<UserProduct>();
    private static long sequence = 1;

    @Autowired
    private IUserService userService;

    @Override
    public void save(List<UserProduct> list) throws BusinessException {
	for (UserProduct up : list) {
	    up.setUser(userService.getUserLogged());
	    up.setId(sequence);
	    sequence++;
	}
	usersProducts.addAll(list);
    }

    @Override
    public void clear(long userId) {
	usersProducts.clear();
    }

    @Override
    public List<UserProduct> getByUserId(long userId) {
	return usersProducts.stream().filter(up -> up.getUser().getId().intValue() == userId)
		.collect(Collectors.toList());
    }

    @Override
    public List<UserProduct> getByProductId(long productId) {
	return usersProducts.stream().filter(up -> up.getId().intValue() == productId).collect(Collectors.toList());
    }

    @Override
    public void removeByProductId(long productId) {
	List<UserProduct> newList = usersProducts.stream().filter(up -> up.getId().intValue() != productId)
		.collect(Collectors.toList());
	usersProducts.clear();
	usersProducts.addAll(newList);
    }

}
