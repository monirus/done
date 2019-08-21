import org.apache.commons.collections4.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Supermarket {
    private static final String filepath = "C:\\Users\\monica.dorhoi\\Desktop\\Plan de invatare JPARD\\ShoppingCart.txt";

    public static void main(String[] args) {
        Supermarket supermarket = new Supermarket();

        // 1. generate cart with 100 products and random prices
        ShoppingCart shoppingCart = supermarket.generateCart();

        // 2. and 3. Print the cart content
        supermarket.printShoppingCart(shoppingCart);

        //4. Save the shopping cart to a file
        supermarket.saveShoppingCartToAFile(shoppingCart);

        //5. Load the shopping cart from the file
        supermarket.loadShoppingCartFromAFile(filepath);
        supermarket.printShoppingCart(shoppingCart);
    }

    private List<Product> generateProducts(int upperLimit) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < upperLimit; i++) {
            String productName = "Product" + i;
            double price = Util.generateRandomPrice();

            // for every iteration (until n), create a new product
            Product product = new Product(productName, "Food", price, i + 1);

            // add the product to the list
            products.add(product);
        }

        return products;
    }

    private ShoppingCart generateCart() {
        //create the shopping cart. Add 30 products directly to the shopping cart
        ShoppingCart shoppingCart = new ShoppingCart();
        addProductsToCart(shoppingCart, 40);

        //create the first bag. Add 20 products to this bag. Add the bag to the shopping cart
        ShoppingCart firstBag = new ShoppingCart();
        addProductsToCart(firstBag, 30);
        double totalPrice = firstBag.calculateTotalPrice(firstBag);
        firstBag.setTotalPrice(totalPrice);
        shoppingCart.addBag(firstBag);

        //create the second bag. Add 2 bags in this bag, first containing 26 products and the second 4 products. Add the bag to the shopping cart
        ShoppingCart secondBag = new ShoppingCart();
        ShoppingCart firstInsideBag = new ShoppingCart();
        addProductsToCart(firstInsideBag, 26);
        double totalPriceFirstInside = firstInsideBag.calculateTotalPrice(firstInsideBag);
        firstInsideBag.setTotalPrice(totalPriceFirstInside);

        ShoppingCart secondInsideBag = new ShoppingCart();
        addProductsToCart(secondInsideBag, 4);
        double totalPriceSecondInside = secondInsideBag.calculateTotalPrice(secondInsideBag);
        secondInsideBag.setTotalPrice(totalPriceSecondInside);

        secondBag.addBag(firstInsideBag);
        secondBag.addBag(secondInsideBag);

        double totalPriceSecondBag = secondBag.calculateTotalPrice(secondBag);
        secondBag.setTotalPrice(totalPriceSecondBag);
        shoppingCart.addBag(secondBag);

        double totalPriceShoppingCart = shoppingCart.calculateTotalPrice(shoppingCart);
        shoppingCart.setTotalPrice(totalPriceShoppingCart);

        return shoppingCart;
    }

    protected void addProductsToCart(ShoppingCart shoppingCart, int i) {
        List<Product> shoppingCartProducts = generateProducts(i);
        shoppingCart.addProducts(shoppingCartProducts);
    }

    private void printShoppingCart(ShoppingCart shoppingCart) {
        printFirstPart(shoppingCart);

        System.out.println("Shopping Cart Bags:");

        List<ShoppingCart> bags = shoppingCart.getBags();
        for (int i = 0; i < bags.size(); i++) {
            ShoppingCart bag = bags.get(i);
            int bagNumber = i + 1;
            System.out.println("Bag " + bagNumber + " ====== TOTAL: " + bag.getTotalPrice());

            List<Product> bagProducts = bag.getProducts();
            if (CollectionUtils.isNotEmpty(bagProducts)) {
                System.out.println("\t" + "Bag products:");
                for (Product product : bagProducts) {
                    System.out.println("\t\t" + "Name: " + product.getName() + ", Category: " + product.getCategory() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
                }
            }

            List<ShoppingCart> insideBags = bag.getBags();
            if (CollectionUtils.isNotEmpty(insideBags)) {

                for (int j = 0; j < insideBags.size(); j++) {
                    ShoppingCart insideBag = insideBags.get(j);
                    int insideBagNumber = j + 1;
                    System.out.println("\t\t" + "Bag " + insideBagNumber + "===== TOTAL: " + insideBag.getTotalPrice());

                    List<Product> insideBagProducts = insideBag.getProducts();
                    if (CollectionUtils.isNotEmpty(insideBagProducts)) {
                        for (Product insideProduct : insideBagProducts) {
                            System.out.println("\t\t\t" + "Name: " + insideProduct.getName() + ", Category: " + insideProduct.getCategory() + ", Price: " + insideProduct.getPrice() + ", Quantity: " + insideProduct.getQuantity());
                        }
                    }
                }
            }

        }

    }

    protected void printFirstPart(ShoppingCart shoppingCart) {
        System.out.println("Shopping Cart:======= TOTAL: " + shoppingCart.getTotalPrice());
        System.out.println("Shopping cart products:");

        List<Product> cartProducts = shoppingCart.getProducts();
        for (Product product : cartProducts) {
            System.out.println("\t" + "Name: " + product.getName() + ", Category: " + product.getCategory() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
        }
    }

    private void saveShoppingCartToAFile(Object serObj) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("You succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Object loadShoppingCartFromAFile(String filepath) {
        try {

            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            System.out.println("The Object has been read from the file");
            objectIn.close();
            return obj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
