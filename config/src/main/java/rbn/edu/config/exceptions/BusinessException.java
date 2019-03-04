package rbn.edu.config.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

    private static final long serialVersionUID = -6845610310422875419L;
    private List<String> errorMessage;

    public BusinessException() {
	errorMessage = new ArrayList<String>();
    }

    public BusinessException(String message) {
	this();
	errorMessage.add(message);
    }

    public BusinessException(List<String> message) {
	this();
	errorMessage.addAll(message);
    }

    @Override
    public String getMessage() {
	return errorMessage.toString();
    }

    public List<String> getMessages() {
	return errorMessage;
    }

}
