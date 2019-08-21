import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
    private List<Product> products;
    private double totalPrice;
    private List<ShoppingCart> bags;

    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.bags = new ArrayList<>();
    }

    public ShoppingCart(List<Product> products, double totalPrice, List<ShoppingCart> bags) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.bags = bags;
    }

    public double calculateTotalPrice(ShoppingCart shoppingCart) {
        double totalPrice = 0.0;

        List<Product> products = shoppingCart.getProducts();
        double totalProductPrice = 0.0;
        for (Product product : products) {
            totalProductPrice = totalProductPrice + product.getPrice();
        }

        //add product total price to total
        totalPrice = totalPrice + totalProductPrice;

        // verify if 5% discount is applicable
        boolean isSmallerDiscountApplicable = false;
        if (products.size() > 5) {
            isSmallerDiscountApplicable = true;
        }

        for (ShoppingCart bag : shoppingCart.getBags()) {
            totalPrice = totalPrice + calculateTotalPrice(bag);

        }

        // verify if the 10% discount is applicable
        boolean isBiggerDiscountApplicable = false;
        if (totalPrice > 1000) {
            isBiggerDiscountApplicable = true;
        }

        // if the biggest discount is available...apply it. If not, if the smaller discount is available...apply it.
        if (isBiggerDiscountApplicable) {
            totalPrice = totalPrice - (totalPrice * 0.1);
        } else if (isSmallerDiscountApplicable) {
            totalPrice = totalPrice - (totalPrice * 0.05);
        }

        return totalPrice;
    }


    public void addBag(ShoppingCart bag) {
        this.bags.add(bag);
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ShoppingCart> getBags() {
        return bags;
    }

    public void setBags(List<ShoppingCart> bags) {
        this.bags = bags;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "products=" + products +
                ", totalPrice=" + totalPrice +
                ", bags=" + bags +
                '}';
    }
}
