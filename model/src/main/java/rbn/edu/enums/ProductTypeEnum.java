package rbn.edu.enums;

public enum ProductTypeEnum {

    UTILITY(0, "Utility"), CAR(1, "Car"), CLOTH(2, "Cloth");

    private ProductTypeEnum(int value, String description) {
	this.value = value;
	this.description = description;
    }

    private int value;
    private String description;

    @Override
    public String toString() {
	return String.valueOf(description);
    }

    public String getDescription() {
	return description;
    }

    public int getValue() {
	return value;
    }
}
