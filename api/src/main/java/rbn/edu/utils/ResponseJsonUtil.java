package rbn.edu.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rbn.edu.model.api.ResponseError;
import rbn.edu.model.api.StatusCodeDetail;

public class ResponseJsonUtil {

    private static ResponseJsonUtil instance;

    private ResponseJsonUtil() {
    }

    public static ResponseJsonUtil get() {
	if (instance == null) {
	    instance = new ResponseJsonUtil();
	}
	return instance;
    }

    public void handleTokenExpiration(ServletResponse response) throws IOException, JsonProcessingException {
	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	ResponseError errorResponse = new ResponseError();
	List<String> list = new ArrayList<String>();
	errorResponse.setMessages(list);
	errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
	errorResponse.setStatusCodeDetail(StatusCodeDetail.TOKEN_EXPIRED);
	list.add(StatusCodeDetail.TOKEN_EXPIRED.toString());
	httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
	httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
    }

    public void sendResponse(ServletResponse response, HttpStatus status, String message)
	    throws IOException, JsonProcessingException {
	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	ResponseError errorResponse = new ResponseError();
	List<String> list = new ArrayList<String>();
	list.add(message);
	errorResponse.setMessages(list);
	errorResponse.setStatusCode(status.value());
	httpServletResponse.setStatus(status.value());
	httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
	if (object == null) {
	    return null;
	}
	ObjectMapper mapper = new ObjectMapper();
	return mapper.writeValueAsString(object);
    }

}
