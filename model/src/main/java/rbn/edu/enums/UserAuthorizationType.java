package rbn.edu.enums;

import java.util.ArrayList;
import java.util.List;

public enum UserAuthorizationType {

    ROLE_ADMIN("ROLE_ADMIN"), ROLE_VISITOR("ROLE_VISITOR"), ROLE_CUSTOMER("ROLE_CUSTOMER");

    private final String value;

    private UserAuthorizationType(final String value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return value;
    }

    public static List<UserAuthorizationType> getValues() {
	List<UserAuthorizationType> niveis = new ArrayList<UserAuthorizationType>();
	niveis.add(ROLE_ADMIN);
	niveis.add(ROLE_VISITOR);
	niveis.add(ROLE_CUSTOMER);
	return niveis;
    }

    public static List<String> getStringValues() {
	List<String> lista = new ArrayList<String>();
	for (UserAuthorizationType tipo : getValues()) {
	    lista.add(tipo.toString());
	}
	return lista;
    }

    public static UserAuthorizationType[] getArrayValues() {
	List<String> lista = new ArrayList<String>();
	for (UserAuthorizationType tipo : getValues()) {
	    lista.add(tipo.toString());
	}
	return (UserAuthorizationType[]) lista.toArray();
    }

}
