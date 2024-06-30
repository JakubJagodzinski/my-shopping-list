package client.customer.customerlist.customerlistaddproduct;

import client.components.*;
import client.customer.customerlist.customerlistedit.CustomerListEditController;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;
import client.Icons;
import common.shoppinglistapi.Product;
import common.shoppinglistapi.UnitType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerListAddProductController {

    private final CustomerListAddProductModel model;
    private final CustomerListAddProductView view;
    private final CustomerListEditController customerListEditController;

    public CustomerListAddProductController(CustomerListAddProductModel model, CustomerListAddProductView view, CustomerListEditController customerListEditController) {
        this.model = model;
        this.view = view;
        this.customerListEditController = customerListEditController;
        ArrayList<String> categoriesNames = this.model.getShopCategoriesNames();
        if (categoriesNames == null || categoriesNames.isEmpty()) {
            this.view.setShopIsEmptyLabel();
        } else {
            this.view.setShopCategoriesComboBox(categoriesNames);
            this.view.setShopCategoryPane(this.createShopCategoryPane(this.view.getSelectedCategory()));
            this.view.addCategoriesComboBoxListener(new CategoriesComboBoxListener());
        }
        this.view.addReturnButtonListener(new ReturnListener());
    }

    public JScrollPane createShopCategoryPane(String categoryName) {
        ArrayList<Product> products = model.getProducts(categoryName);

        DarkPanel shopCategoryPanel = new DarkPanel();
        shopCategoryPanel.setLayout(new BoxLayout(shopCategoryPanel, BoxLayout.Y_AXIS));

        if (products != null && !products.isEmpty()) {
            DarkPanel headerPanel = createCategoryHeaderPanel(products.size());
            shopCategoryPanel.add(headerPanel, BorderLayout.NORTH);
            shopCategoryPanel.add(this.createProductsInfoHeader());
            for (var product : products) {
                shopCategoryPanel.add(this.createProductPanel(categoryName, product));
            }
        }

        JScrollPane shopCategoryPane = new JScrollPane(shopCategoryPanel);
        shopCategoryPane.setBackground(Colors.BACKGROUND_COLOR);
        shopCategoryPane.setForeground(Colors.FOREGROUND_COLOR);
        shopCategoryPane.setPreferredSize(new Dimension(1400, 700));

        JScrollBar verticalScrollBar = shopCategoryPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(10);

        return shopCategoryPane;
    }

    public DarkPanel createCategoryHeaderPanel(int productsSize) {
        DarkPanel headerPanel = new DarkPanel();
        headerPanel.setBorder(new LineBorder(Color.WHITE));

        GridPanel nameAndSizePanel = new GridPanel(2, 1);

        DarkPanel categoryNameLabelPanel = new DarkPanel();
        categoryNameLabelPanel.add(this.view.getCategoriesComboBox());

        nameAndSizePanel.add(categoryNameLabelPanel);

        String categorySizeString = "(" + (productsSize == 0 ? "empty" : (productsSize == 1 ? productsSize + " product" : productsSize + " products")) + ")";

        DarkPanel categorySizeLabelPanel = new DarkPanel();
        categorySizeLabelPanel.add(new BoldLabel(categorySizeString));

        nameAndSizePanel.add(categorySizeLabelPanel);

        headerPanel.add(nameAndSizePanel);

        return headerPanel;
    }

    public GridPanel createProductsInfoHeader() {
        GridPanel productsInfoHeader = new GridPanel(1, 6);
        productsInfoHeader.setBorder(new LineBorder(Color.WHITE));

        DarkPanel productName = new DarkPanel();
        productName.add(new BoldLabel("product name"));
        productsInfoHeader.add(productName);

        DarkPanel unitPrice = new DarkPanel();
        unitPrice.add(new BoldLabel("unit price"));
        productsInfoHeader.add(unitPrice);

        DarkPanel currencyType = new DarkPanel();
        currencyType.add(new BoldLabel("currency"));
        productsInfoHeader.add(currencyType);

        DarkPanel unitSize = new DarkPanel();
        unitSize.add(new BoldLabel("unit size"));
        productsInfoHeader.add(unitSize);

        DarkPanel unitType = new DarkPanel();
        unitType.add(new BoldLabel("unit type"));
        productsInfoHeader.add(unitType);

        DarkPanel add = new DarkPanel();
        add.add(new BoldLabel("add"));
        productsInfoHeader.add(add);

        return productsInfoHeader;
    }

    public GridPanel createProductPanel(String categoryName, Product product) {
        GridPanel productPanel = new GridPanel(1, 6);
        productPanel.setBorder(new LineBorder(Color.DARK_GRAY));

        DarkPanel productName = new DarkPanel();
        productName.add(new PlainLabel(product.getProductName()));
        productPanel.add(productName);

        DarkPanel unitPrice = new DarkPanel();
        unitPrice.add(new PlainLabel(String.valueOf(product.getUnitPrice())));
        productPanel.add(unitPrice);

        DarkPanel currencyType = new DarkPanel();
        currencyType.add(new PlainLabel(String.valueOf(product.getCurrencyType())));
        productPanel.add(currencyType);

        DarkPanel unitSize = new DarkPanel();
        UnitType productUnitType = product.getUnitType();
        boolean toFloat = productUnitType.equals(UnitType.l) || productUnitType.equals(UnitType.kg);
        if (toFloat) {
            unitSize.add(new PlainLabel(String.valueOf(product.getUnitSize())));
        } else {
            unitSize.add(new PlainLabel(String.valueOf(Math.round(product.getUnitSize()))));
        }
        productPanel.add(unitSize);

        DarkPanel unitType = new DarkPanel();
        unitType.add(new PlainLabel(String.valueOf(product.getUnitType())));
        productPanel.add(unitType);

        DarkPanel addProductButtonPanel = new DarkPanel();

        GreenButton addProductButton = new GreenButton(Icons.PLUS_SIGN_ICON + " 1", 40, 20);
        addProductButton.setForegroundColor(Colors.FOREGROUND_COLOR);
        addProductButton.addActionListener(new AddProductListener(categoryName, product.getProductName()));
        addProductButtonPanel.add(addProductButton);

        productPanel.add(addProductButtonPanel);

        return productPanel;
    }

    public void refreshShopCategoryPanel() {
        String categoryName = this.view.getSelectedCategory();
        if (categoryName != null) {
            JScrollPane newShoppingListPanel = createShopCategoryPane(categoryName);
            if (newShoppingListPanel != null) {
                this.view.updateShopCategoryPane(newShoppingListPanel);
            }
        }
    }

    public class CategoriesComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshShopCategoryPanel();
        }
    }

    public class AddProductListener implements ActionListener {

        private final String categoryName;
        private final String productName;

        public AddProductListener(String categoryName, String productName) {
            this.categoryName = categoryName;
            this.productName = productName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            model.addProduct(this.categoryName, this.productName);
            customerListEditController.setSaveButtonRed();
        }
    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            customerListEditController.refreshShoppingListPanel();
            ViewManager.show(ViewNames.CUSTOMER_LIST_EDIT);
            ViewManager.remove(ViewNames.CUSTOMER_LIST_ADD_PRODUCT);
        }
    }

}
