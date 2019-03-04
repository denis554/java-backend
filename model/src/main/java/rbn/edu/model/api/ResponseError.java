package rbn.edu.model.api;

import java.util.List;

public class ResponseError {

    private int statusCode;
    private StatusCodeDetail statusCodeDetail;

    private List<String> messages;

    public int getStatusCode() {
	return statusCode;
    }

    public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
    }

    public List<String> getMessages() {
	return messages;
    }

    public void setMessages(List<String> messages) {
	this.messages = messages;
    }

    public StatusCodeDetail getStatusCodeDetail() {
	return statusCodeDetail;
    }

    public void setStatusCodeDetail(StatusCodeDetail statusCodeDetail) {
	this.statusCodeDetail = statusCodeDetail;
    }

}