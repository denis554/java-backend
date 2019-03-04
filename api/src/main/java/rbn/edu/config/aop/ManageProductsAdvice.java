package rbn.edu.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rbn.edu.config.websockets.GetAllProductsWebSocketHandler;

@Aspect
@Component
public class ManageProductsAdvice {

    @Autowired
    private GetAllProductsWebSocketHandler getAllProductsWebSocketHandler;

    @After("execution(* rbn.edu.service.IProductService.add(..)) " + //
	    "|| execution(* rbn.edu.service.IProductService.update(..)) " + //
	    "|| execution(* rbn.edu.service.IProductService.remove(..))")
    public void inWebLayer(JoinPoint joinPoint) {
	getAllProductsWebSocketHandler.updateListeners();
    }

}
