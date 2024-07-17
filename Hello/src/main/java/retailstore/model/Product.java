package retailstore.model;

public class Product {
    private String name;
    private double price;
    private boolean isGrocery;

    public Product(String name, double price, boolean isGrocery) {
        this.name = name;
        this.price = price;
        this.isGrocery = isGrocery;
    }

    public double getPrice() {
        return price;
    }

    public boolean isGrocery() {
        return isGrocery;
    }
}
