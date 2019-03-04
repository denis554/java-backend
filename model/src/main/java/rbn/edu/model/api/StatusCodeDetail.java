package rbn.edu.model.api;

public enum StatusCodeDetail {

    TOKEN_EXPIRED("Token has expired");

    private String value;

    private StatusCodeDetail(String value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return this.value;
    }

}
