package rbn.edu.model.api;

import java.util.List;

public class ResponseServer<T> {

    private int size;
    private List<T> data;

    public ResponseServer() {
    }

    public ResponseServer(int size, List<T> data) {
	super();
	this.size = size;
	this.data = data;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
    }

    public List<T> getData() {
	return data;
    }

    public void setData(List<T> data) {
	this.data = data;
    }

}
