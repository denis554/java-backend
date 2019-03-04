package rbn.edu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import rbn.edu.config.ProjectConstants;
import rbn.edu.config.exceptions.BusinessException;
import rbn.edu.dao.IUserProductDAO;
import rbn.edu.model.User;
import rbn.edu.model.UserProduct;
import rbn.edu.service.IUserProductService;
import rbn.edu.service.IUserService;

@Service
@Transactional
public class UserProductService implements IUserProductService {

    private static final long serialVersionUID = -3666577666403707888L;

    @Autowired
    private IUserProductDAO userProductDAO;

    @Autowired
    private IUserService userService;

    @Autowired
    private Environment env;

    @Override
    public void save(List<UserProduct> list) throws BusinessException {
	if (CollectionUtils.isEmpty(list)) {
	    throw new BusinessException(env.getProperty(ProjectConstants.USER_PRODUCT_NULL));
	}
	User user = userService.getUserLogged();
	clear(user.getId());
	list.forEach(userProduct -> userProduct.setUser(user));
	userProductDAO.save(list);
    }

    @Override
    public void clear(long userId) {
	userProductDAO.clear(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProduct> getByUserId(long userId) {
	List<UserProduct> list = userProductDAO.getByUserId(userId);
	for (UserProduct up : list) {
	    up.getUser().setUserLevels(null);
	    up.getUser().setPassword(null);
	}
	return list;
    }

    @Override
    public List<UserProduct> getByProductId(long productId) {
	return userProductDAO.getByProductId(productId);
    }

    @Override
    public void removeByProductId(long productId) {
	userProductDAO.removeByProductId(productId);
    }

}
