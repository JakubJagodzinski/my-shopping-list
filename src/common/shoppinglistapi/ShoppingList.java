package common.shoppinglistapi;

import server.loadedcustomerlists.ShoppingListInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

public class ShoppingList {

    private final String editorName;
    private final ShoppingListInfo info;
    private ConcurrentSkipListMap<String, Category> categories;
    private CurrencyType currencyType;
    public static final double[][] exchangeRates =
        //PLN   USD   EUR   GBP
        {{1.00, 0.25, 0.23, 0.20}, // PLN
         {3.94, 1.00, 0.92, 0.78}, // USD
         {4.29, 1.09, 1.00, 0.85}, // EUR
         {5.04, 1.28, 1.18, 1.00}};// GBP

    public ShoppingList(String listName, String ownerName, String editorName, String shopName) {
        this.editorName = editorName;
        this.info = new ShoppingListInfo(ownerName, shopName, listName);
        this.currencyType = CurrencyType.PLN;
        this.categories = new ConcurrentSkipListMap<>();
    }

    public ShoppingListInfo info() {
        return this.info;
    }

    public String getOwnerName() {
        return this.info.ownerName();
    }

    public String getListName() {
        return this.info.listName();
    }

    public String getEditorName() {
        return this.editorName;
    }

    public synchronized void clear() {
        this.categories.clear();
    }

    public int size() {
        return this.categories.size();
    }

    public boolean isEmpty() {
        return this.categories.isEmpty();
    }

    public synchronized double totalPrice() {
        double totalPrice = 0;
        for (var categoryName : this.categories.keySet()) {
            totalPrice += this.categories.get(categoryName).totalPrice(this.currencyType);
        }
        return totalPrice;
    }

    public CurrencyType getCurrencyType() {
        return this.currencyType;
    }

    public synchronized void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public synchronized ArrayList<String> getCategoriesNames() {
        return new ArrayList<>(this.categories.keySet());
    }

    public synchronized ArrayList<String> getProductsNames(String categoryName) {
        if (this.containsCategory(categoryName)) {
            return this.categories.get(categoryName).getProductsNames();
        }
        return null;
    }

    public synchronized ArrayList<Product> getProducts(String categoryName) {
        if (this.containsCategory(categoryName)) {
            return this.categories.get(categoryName).getProductsCopies();
        }
        return null;
    }

    public synchronized int getProductQuantity(String categoryName, String productName) {
        if (this.containsProductInCategory(categoryName, productName)) {
            return this.categories.get(categoryName).getProductQuantity(productName);
        }
        return 0;
    }

    public boolean containsCategory(String categoryName) {
        return this.categories.containsKey(categoryName);
    }

    public boolean isCategoryEmpty(String categoryName) {
        return this.containsCategory(categoryName) && this.categories.get(categoryName).isEmpty();
    }

    public synchronized boolean addCategory(String categoryName) {
        if (this.containsCategory(categoryName)) {
            return false;
        }
        this.categories.put(categoryName, new Category());
        return true;
    }

    public synchronized boolean removeCategory(String categoryName) {
        if (this.containsCategory(categoryName)) {
            this.categories.remove(categoryName);
            return true;
        }
        return false;
    }

    public synchronized boolean containsProductInCategory(String categoryName, String productName) {
        return this.containsCategory(categoryName) && this.categories.get(categoryName).containsProduct(productName);
    }

    public synchronized Product copyProduct(String categoryName, String product) {
        return this.categories.get(categoryName).getProductCopy(product);
    }

    public synchronized boolean addProduct(String categoryName, Product product) {
        if (!this.containsCategory(categoryName)) {
            if (!this.addCategory(categoryName)) {
                return false;
            }
        }
        if (!this.containsProductInCategory(categoryName, product.getProductName())) {
            this.categories.get(categoryName).addProduct(product);
            return true;
        }
        return false;
    }

    public synchronized boolean removeProduct(String categoryName, String productName) {
        if (this.containsProductInCategory(categoryName, productName)) {
            return this.categories.get(categoryName).removeProduct(productName);
        }
        return true;
    }

    public synchronized boolean addUnitsOfProduct(String categoryName, String productName, int unitsToAdd) {
        if (this.containsProductInCategory(categoryName, productName)) {
            this.categories.get(categoryName).addUnitsOfProduct(productName, unitsToAdd);
            return true;
        }
        return false;
    }

    public synchronized boolean removeUnitsOfProduct(String categoryName, String productName, int unitsToRemove) {
        if (this.containsProductInCategory(categoryName, productName)) {
            this.categories.get(categoryName).removeUnitsOfProduct(productName, unitsToRemove);
            return true;
        }
        return false;
    }

    public boolean loadFromFile(String path) {
        try {
            Scanner fileReader = new Scanner(new File(path));
            ConcurrentSkipListMap<String, Category> categories = new ConcurrentSkipListMap<>();
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                if (!line.isEmpty()) {
                    String[] data = line.split(";");
                    String categoryName = data[0];
                    String productName = data[1];
                    int productQuantity = Integer.parseInt(data[2]);
                    double unitPrice = Double.parseDouble(data[3]);
                    CurrencyType currencyType = CurrencyType.valueOf(data[4]);
                    double unitSize = Double.parseDouble(data[5]);
                    UnitType unitType = UnitType.valueOf(data[6]);
                    Product newProduct = new Product(productName, productQuantity, unitPrice, currencyType, unitSize, unitType);
                    if (!categories.containsKey(categoryName)) {
                        categories.put(categoryName, new Category());
                    }
                    categories.get(categoryName).addProduct(newProduct);
                }
            }
            fileReader.close();
            this.categories = categories;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveToFile(String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            for (var categoryName : this.categories.keySet()) {
                ArrayList<Product> products = this.getProducts(categoryName);
                for (var product : products) {
                    bufferedWriter.write(categoryName + ";" + product.getProductName() + ";" + product.getQuantity() + ";" + product.getUnitPrice() + ";" + product.getCurrencyType() + ";" + product.getUnitSize() + ";" + product.getUnitType());
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
