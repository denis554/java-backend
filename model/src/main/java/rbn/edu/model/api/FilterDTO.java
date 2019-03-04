package rbn.edu.model.api;

public class FilterDTO<T> {

    private int offset;
    private int pageSize;
    private T filter;
    private boolean orderAsc;
    private String orderProperty;

    public int getOffset() {
	return offset;
    }

    public void setOffset(int offset) {
	this.offset = offset;
    }

    public int getPageSize() {
	return pageSize;
    }

    public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
    }

    public T getFilter() {
	return filter;
    }

    public void setFilter(T filter) {
	this.filter = filter;
    }

    public String getOrderProperty() {
	return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
	this.orderProperty = orderProperty;
    }

    public boolean isOrderAsc() {
	return orderAsc;
    }

    public void setOrderAsc(boolean orderAsc) {
	this.orderAsc = orderAsc;
    }

}
