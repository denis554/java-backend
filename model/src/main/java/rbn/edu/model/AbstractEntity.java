package rbn.edu.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity<PK> implements Serializable {

    private static final long serialVersionUID = 6588334564743977775L;
    public static final String PK = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected PK id;

    public PK getId() {
	return id;
    }

    public void setId(PK id) {
	this.id = id;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AbstractEntity<PK> other = (AbstractEntity<PK>) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

}
