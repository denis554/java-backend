package rbn.edu.config.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import rbn.edu.model.api.ResponseError;

@ControllerAdvice
class GlobalDefaultExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseError handleError400(BusinessException ex) {
	ResponseError error = new ResponseError();
	error.setStatusCode(HttpStatus.BAD_REQUEST.value());
	List<String> list = new ArrayList<String>();
	list.addAll(ex.getMessages());
	error.setMessages(list);
	return error;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseBody
    public ResponseError handleError401(org.springframework.security.access.AccessDeniedException e) {
	ResponseError error = new ResponseError();
	error.setStatusCode(HttpStatus.FORBIDDEN.value());
	List<String> list = new ArrayList<String>();
	list.add("Forbidden");
	error.setMessages(list);
	return error;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseError handleError401BadCredentials(BadCredentialsException e) {
	ResponseError error = new ResponseError();
	error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
	List<String> list = new ArrayList<String>();
	list.add("Unauthorized");
	error.setMessages(list);
	return error;
    }

}