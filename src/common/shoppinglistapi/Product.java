package common.shoppinglistapi;

import java.io.Serializable;

public class Product implements Serializable {

    private final String productName;
    private int quantity;
    private final double unitPrice;
    private final CurrencyType currencyType;
    private final double unitSize;
    private final UnitType unitType;

    public Product(String productName, int quantity, double unitPrice, CurrencyType currencyType, double unitSize, UnitType unitType) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.currencyType = currencyType;
        this.unitSize = unitSize;
        this.unitType = unitType;
    }

    public String getProductName() {
        return this.productName;
    }

    public synchronized int getQuantity() {
        return this.quantity;
    }

    public synchronized double getUnitPrice() {
        return this.unitPrice;
    }

    public synchronized double getUnitPrice(CurrencyType currencyType) {
        return this.unitPrice * ShoppingList.exchangeRates[this.currencyType.ordinal()][currencyType.ordinal()];
    }

    public synchronized CurrencyType getCurrencyType() {
        return this.currencyType;
    }

    public synchronized double getUnitSize() {
        return this.unitSize;
    }

    public synchronized UnitType getUnitType() {
        return this.unitType;
    }

    public synchronized Product getProductCopy() {
        return new Product(this.productName, 1, this.unitPrice, this.currencyType, this.unitSize, this.unitType);
    }

    public synchronized Product getProductCopyWithQuantity() {
        return new Product(this.productName, this.quantity, this.unitPrice, this.currencyType, this.unitSize, this.unitType);
    }

    public synchronized void decreaseQuantity(int number) {
        this.quantity = Math.max(0, this.quantity - number);
    }

    public synchronized void increaseQuantity(int number) {
        this.quantity = Math.max(0, this.quantity + number);
    }

}
