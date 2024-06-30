package client.shop.shopaddproduct;

import client.Icons;
import client.InputValidator;
import client.components.*;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.UnitType;

import java.awt.*;
import java.awt.event.ActionListener;

public class ShopAddProductView extends DarkPanel {

    private final RoundButton addButton;
    private final RoundButton returnButton;

    private final DarkTextField categoryNameField;
    private final DarkTextField productNameField;
    private final DarkTextField productQuantityField;
    private final DarkTextField productUnitPriceField;
    private final DarkComboBox productCurrencyTypeComboBox;
    private final DarkTextField productUnitSizeField;
    private final DarkComboBox productUnitTypeComboBox;

    public ShopAddProductView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Add product"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Category name (" + InputValidator.CATEGORY_NAME_MIN_LENGTH + " - " + InputValidator.CATEGORY_NAME_MAX_LENGTH + " chars):"), gbc);
        gbc.gridy++;

        this.categoryNameField = new DarkTextField(20);
        centerPanel.add(this.categoryNameField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Product name: (" + InputValidator.PRODUCT_NAME_MIN_LENGTH + " - " + InputValidator.PRODUCT_NAME_MAX_LENGTH + " chars)"), gbc);
        gbc.gridy++;

        this.productNameField = new DarkTextField(20);
        centerPanel.add(this.productNameField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Quantity:"), gbc);
        gbc.gridy++;

        this.productQuantityField = new DarkTextField(20);
        centerPanel.add(this.productQuantityField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Unit price:"), gbc);
        gbc.gridy++;

        this.productUnitPriceField = new DarkTextField(20);
        centerPanel.add(this.productUnitPriceField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Currency type:"), gbc);
        gbc.gridy++;

        this.productCurrencyTypeComboBox = new DarkComboBox(CurrencyType.getValues());
        centerPanel.add(this.productCurrencyTypeComboBox, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Unit size:"), gbc);
        gbc.gridy++;

        this.productUnitSizeField = new DarkTextField(20);
        centerPanel.add(this.productUnitSizeField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Unit type:"), gbc);
        gbc.gridy++;

        this.productUnitTypeComboBox = new DarkComboBox(UnitType.getValues());
        centerPanel.add(this.productUnitTypeComboBox, gbc);
        gbc.gridy++;

        this.addButton = new GreenButton("Confirm " + Icons.TICK_ICON);
        centerPanel.add(this.addButton, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);
        gbc.gridy++;

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void clearFields() {
        this.categoryNameField.setText("");
        this.productNameField.setText("");
        this.productQuantityField.setText("");
        this.productUnitPriceField.setText("");
        this.productCurrencyTypeComboBox.setSelectedIndex(0);
        this.productUnitSizeField.setText("");
        this.productUnitTypeComboBox.setSelectedIndex(0);
    }

    public String getCategoryName() {
        return this.categoryNameField.getText().strip();
    }

    public String getProductName() throws Exception {
        String productName = this.productNameField.getText().strip();
        if (productName.isEmpty()) {
            throw new Exception();
        }
        return productName;
    }

    public int getProductQuantity() throws Exception {
        try {
            int quantity = Integer.parseInt(this.productQuantityField.getText().strip());
            if (quantity < 1) {
                throw new Exception();
            }
            return quantity;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public double getProductUnitPrice() throws Exception {
        try {
            double unitPrice = Double.parseDouble(this.productUnitPriceField.getText().strip());
            if (unitPrice <= 0) {
                throw new Exception();
            }
            return unitPrice;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public CurrencyType getProductCurrencyType() {
        return CurrencyType.valueOf((String) this.productCurrencyTypeComboBox.getSelectedItem());
    }

    public double getProductUnitSize() throws Exception {
        try {
            double unitSize = Double.parseDouble(this.productUnitSizeField.getText().strip());
            if (unitSize <= 0) {
                throw new Exception();
            }
            return unitSize;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public UnitType getProductUnitType() throws Exception {
        try {
            return UnitType.valueOf((String) this.productUnitTypeComboBox.getSelectedItem());
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void addAddButtonListener(ActionListener actionListener) {
        this.addButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
