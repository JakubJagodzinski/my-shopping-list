package common.shoppinglistapi;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;

public class Category {

    private final ConcurrentSkipListMap<String, Product> products;

    public Category() {
        this.products = new ConcurrentSkipListMap<>();
    }

    public int size() {
        return this.products.size();
    }

    public boolean isEmpty() {
        return this.products.isEmpty();
    }

    public synchronized double totalPrice(CurrencyType currencyType) {
        double totalPrice = 0;
        for (var product : this.products.values()) {
            totalPrice += product.getUnitPrice(currencyType) * product.getQuantity();
        }
        return totalPrice;
    }

    public synchronized boolean containsProduct(String productName) {
        return this.products.containsKey(productName);
    }

    public synchronized int getProductQuantity(String productName) {
        return this.products.get(productName).getQuantity();
    }

    public synchronized ArrayList<String> getProductsNames() {
        ArrayList<String> products = new ArrayList<>();
        for (var product : this.products.values()) {
            products.add(product.getProductName());
        }
        return products;
    }

    public synchronized ArrayList<Product> getProductsCopies() {
        ArrayList<Product> productsCopies = new ArrayList<>();
        for (var productName : this.products.keySet()) {
            productsCopies.add(this.getProductCopyWithQuantity(productName));
        }
        return productsCopies;
    }

    public synchronized Product getProductCopy(String productName) {
        return this.products.get(productName).getProductCopy();
    }

    public synchronized Product getProductCopyWithQuantity(String productName) {
        return this.products.get(productName).getProductCopyWithQuantity();
    }

    public synchronized void addProduct(Product newProduct) {
        this.products.put(newProduct.getProductName(), newProduct);
    }

    public synchronized boolean removeProduct(String productName) {
        if (this.containsProduct(productName)) {
            return this.products.remove(productName) != null;
        }
        return false;
    }

    public synchronized void addUnitsOfProduct(String productName, int unitsToAdd) {
        this.products.get(productName).increaseQuantity(unitsToAdd);
    }

    public synchronized void removeUnitsOfProduct(String productName, int unitsToRemove) {
        this.products.get(productName).decreaseQuantity(unitsToRemove);
    }

}
