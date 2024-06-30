package client.shop.shopedit;

import client.Icons;
import client.components.*;
import client.shop.shopaddproduct.ShopAddProductController;
import client.shop.shopaddproduct.ShopAddProductModel;
import client.shop.shopaddproduct.ShopAddProductView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.Product;
import common.shoppinglistapi.UnitType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShopEditController {

    private final ShopEditModel model;
    private final ShopEditView view;

    public ShopEditController(ShopEditModel model, ShopEditView view) {
        this.model = model;
        this.view = view;
        this.view.addAddProductButtonListener(new AddProductListener(this));
        this.view.addReloadLocalButtonListener(new ReloadLocalListener());
        this.view.addSaveLocalButtonListener(new SaveLocalListener());
        this.view.addReloadPublicButtonListener(new ReloadPublicListener());
        this.view.addPushTuPublicButtonListener(new PushToPublicListener());
        this.view.addDeleteButtonListener(new DeleteListener());
        this.view.addCloseButtonListener(new CloseListener());
        this.view.addClearButtonListener(new ClearListener());
        this.view.setShopPane(this.createShopPanel());
        this.view.setShopTotalPriceLabel(this.createListTotalPriceLabel());
        this.view.setShopCurrencyComboBox(this.createShopCurrencyComboBox());
        this.view.addShopCurrencyComboBoxListener(new CurrencyComboBoxListener());
    }

    public BoldLabel createListTotalPriceLabel() {
        return new BoldLabel("Total price: " + this.model.getTotalPrice());
    }

    public void refreshShopPanel() {
        this.view.setShopPane(this.createShopPanel());
        this.refreshShopTotalPricePanel();
    }

    public void refreshShopTotalPricePanel() {
        this.view.setShopTotalPriceLabel(this.createListTotalPriceLabel());
        DarkComboBox listCurrencyComboBox = this.view.getShopCurrencyComboBox();
        listCurrencyComboBox.setSelectedItem(this.model.getCurrencyType().toString());
        this.view.setShopCurrencyComboBox(listCurrencyComboBox);
    }

    public void setSaveButtonRed() {
        this.view.setSaveButtonColorRed();
    }

    public void setSaveButtonNormal() {
        this.view.setSaveButtonColorNormal();
    }

    public DarkComboBox createShopCurrencyComboBox() {
        DarkComboBox shopCurrencyComboBox = new DarkComboBox(CurrencyType.getValues());
        shopCurrencyComboBox.setSelectedItem(this.model.getCurrencyType());
        return shopCurrencyComboBox;
    }

    public JScrollPane createShopPanel() {
        ArrayList<String> categoriesNames = this.model.getCategoriesNames();

        DarkPanel shopPanel = new DarkPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));

        if (categoriesNames.isEmpty()) {
            shopPanel.add(this.createShopIsEmptyPanel());
        } else {
            for (var categoryName : categoriesNames) {
                shopPanel.add(this.createCategoryPanel(categoryName));
            }
        }

        JScrollPane shoppingListPane = new JScrollPane(shopPanel);
        shoppingListPane.setBackground(Colors.BACKGROUND_COLOR);
        shoppingListPane.setForeground(Colors.FOREGROUND_COLOR);
        shoppingListPane.setPreferredSize(new Dimension(1600, 750));

        JScrollBar verticalScrollBar = shoppingListPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(10);

        return shoppingListPane;
    }

    public GridPanel createShopIsEmptyPanel() {
        GridPanel shopIsEmptyPanel = new GridPanel(2, 1);
        shopIsEmptyPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        shopIsEmptyPanel.add(new HeaderLabel("Oops! It seems that your shop is empty..."), gbc);
        gbc.gridy++;

        shopIsEmptyPanel.add(new JLabel(ApplicationLogo.getImageIcon("emptyListIcon.png")), gbc);

        return shopIsEmptyPanel;
    }

    public DarkPanel createCategoryPanel(String categoryName) {
        ArrayList<Product> products = model.getProducts(categoryName);

        DarkPanel categoryPanel = new DarkPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));

        GridPanel headerPanel = createCategoryHeaderPanel(categoryName, products.size());

        categoryPanel.add(headerPanel, BorderLayout.NORTH);

        if (!products.isEmpty()) {
            categoryPanel.add(this.createProductsInfoHeader());
            for (var product : products) {
                categoryPanel.add(this.createProductPanel(categoryName, product));
            }
        }

        return categoryPanel;
    }

    public GridPanel createCategoryHeaderPanel(String categoryName, int productsSize) {
        GridPanel headerPanel = new GridPanel(1, 3);
        headerPanel.setBorder(new LineBorder(Color.BLACK));

        DarkPanel dummyPanel = new DarkPanel();
        dummyPanel.setBackground(Colors.CATEGORY_HEADER_COLOR);

        headerPanel.add(dummyPanel);

        GridPanel nameAndSizePanel = new GridPanel(2, 1);

        DarkPanel categoryNameLabelPanel = new DarkPanel();
        categoryNameLabelPanel.setBackground(Colors.CATEGORY_HEADER_COLOR);
        categoryNameLabelPanel.add(new HeaderLabel(categoryName, Color.BLACK, 20));

        nameAndSizePanel.add(categoryNameLabelPanel);

        String categorySizeString = "(" + (productsSize == 0 ? "empty" : (productsSize == 1 ? productsSize + " product" : productsSize + " products")) + ")";

        DarkPanel categorySizeLabelPanel = new DarkPanel();
        categorySizeLabelPanel.setBackground(Colors.CATEGORY_HEADER_COLOR);
        categorySizeLabelPanel.add(new BoldLabel(categorySizeString, Color.BLACK));

        nameAndSizePanel.add(categorySizeLabelPanel);

        headerPanel.add(nameAndSizePanel);

        DarkPanel deleteCategoryButtonPanel = new DarkPanel();
        deleteCategoryButtonPanel.setBackground(Colors.CATEGORY_HEADER_COLOR);
        deleteCategoryButtonPanel.add(createDeleteCategoryButton(categoryName));

        headerPanel.add(deleteCategoryButtonPanel);

        return headerPanel;
    }

    public RedButton createDeleteCategoryButton(String categoryName) {
        RedButton deleteCategoryButton = new RedButton("Remove category", 120, 50);
        deleteCategoryButton.setForegroundColor(Colors.FOREGROUND_COLOR);
        deleteCategoryButton.addActionListener(new RemoveCategoryListener(categoryName));

        return deleteCategoryButton;
    }

    public GridPanel createProductsInfoHeader() {
        GridPanel productsInfoHeader = new GridPanel(1, 8);
        productsInfoHeader.setBorder(new LineBorder(Color.WHITE));

        DarkPanel productQuantity = new DarkPanel();
        productQuantity.add(new BoldLabel("quantity"));
        productsInfoHeader.add(productQuantity);

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

        DarkPanel edit = new DarkPanel();
        edit.add(new BoldLabel("edit"));
        productsInfoHeader.add(edit);

        DarkPanel remove = new DarkPanel();
        remove.add(new BoldLabel("remove"));
        productsInfoHeader.add(remove);

        return productsInfoHeader;
    }

    public GridPanel createProductPanel(String categoryName, Product product) {
        GridPanel productPanel = new GridPanel(1, 8);
        productPanel.setBorder(new LineBorder(Color.DARK_GRAY));

        DarkPanel productQuantity = new DarkPanel();

        PlainLabel quantityLabel = new PlainLabel(String.valueOf(product.getQuantity()));

        RedButton decreaseQuantityButton = new RedButton(Icons.MINUS_SIGN_ICON, 20, 20);
        decreaseQuantityButton.setForegroundColor(Colors.FOREGROUND_COLOR);
        decreaseQuantityButton.addActionListener(new DecreaseProductQuantityListener(categoryName, product.getProductName(), quantityLabel));
        productQuantity.add(decreaseQuantityButton);

        productQuantity.add(quantityLabel);

        GreenButton increaseQuantityButton = new GreenButton(Icons.PLUS_SIGN_ICON, 20, 20);
        increaseQuantityButton.setForegroundColor(Colors.FOREGROUND_COLOR);
        increaseQuantityButton.addActionListener(new IncreaseProductQuantityListener(categoryName, product.getProductName(), quantityLabel));
        productQuantity.add(increaseQuantityButton);

        productPanel.add(productQuantity);

        DarkPanel productNamePanel = new DarkPanel();
        PlainLabel productNameLabel = new PlainLabel(product.getProductName());
        productNamePanel.add(productNameLabel);
        DarkTextField productNameTextField = new DarkTextField(15, 14);
        productNameTextField.setVisible(false);
        productNamePanel.add(productNameTextField);
        productPanel.add(productNamePanel);

        DarkPanel unitPricePanel = new DarkPanel();
        PlainLabel unitPriceLabel = new PlainLabel(String.valueOf(product.getUnitPrice()));
        unitPricePanel.add(unitPriceLabel);
        DarkTextField unitPriceTextField = new DarkTextField(5, 14);
        unitPriceTextField.setVisible(false);
        unitPricePanel.add(unitPriceTextField);
        productPanel.add(unitPricePanel);

        DarkPanel currencyTypePanel = new DarkPanel();
        PlainLabel currencyTypeLabel = new PlainLabel(String.valueOf(product.getCurrencyType()));
        currencyTypePanel.add(currencyTypeLabel);
        DarkComboBox currencyTypeComboBox = new DarkComboBox(CurrencyType.getValues());
        currencyTypeComboBox.setVisible(false);
        currencyTypePanel.add(currencyTypeComboBox);
        productPanel.add(currencyTypePanel);

        DarkPanel unitSizePanel = new DarkPanel();
        PlainLabel unitSizeLabel;
        UnitType productUnitType = product.getUnitType();
        boolean toFloat = productUnitType.equals(UnitType.l) || productUnitType.equals(UnitType.kg);
        if (toFloat) {
            unitSizeLabel = new PlainLabel(String.valueOf(product.getUnitSize()));
        } else {
            unitSizeLabel = new PlainLabel(String.valueOf(Math.round(product.getUnitSize())));
        }
        unitSizePanel.add(unitSizeLabel);
        DarkTextField unitSizeTextField = new DarkTextField(5, 14);
        unitSizeTextField.setVisible(false);
        unitSizePanel.add(unitSizeTextField);
        productPanel.add(unitSizePanel);

        DarkPanel unitTypePanel = new DarkPanel();
        PlainLabel unitTypeLabel = new PlainLabel(String.valueOf(product.getUnitType()));
        unitTypePanel.add(unitTypeLabel);
        DarkComboBox unitTypeComboBox = new DarkComboBox(UnitType.getValues());
        unitTypeComboBox.setVisible(false);
        unitTypePanel.add(unitTypeComboBox);
        productPanel.add(unitTypePanel);

        DarkPanel editProductButtonPanel = new DarkPanel();

        YellowButton editProductButton = new YellowButton(Icons.EDIT_ICON, 20, 20);
        editProductButton.setForegroundColor(Colors.FOREGROUND_COLOR);
        editProductButton.addActionListener(new EditProductListener(editProductButton, editProductButtonPanel, categoryName, product, productNameLabel, productNameTextField, unitPriceLabel, unitPriceTextField, currencyTypeLabel, currencyTypeComboBox, unitSizeLabel, unitSizeTextField, unitTypeLabel, unitTypeComboBox));

        editProductButtonPanel.add(editProductButton);
        productPanel.add(editProductButtonPanel);

        DarkPanel deleteProductButtonPanel = new DarkPanel();

        RedButton deleteProductButton = new RedButton(Icons.CROSS_ICON, 20, 20);
        deleteProductButton.setForegroundColor(Colors.FOREGROUND_COLOR);
        deleteProductButton.addActionListener(new RemoveProductListener(categoryName, product.getProductName()));

        deleteProductButtonPanel.add(deleteProductButton);
        productPanel.add(deleteProductButtonPanel);

        return productPanel;
    }

    public class CurrencyComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.changeCurrencyType(view.getSelectedCurrencyType());
            refreshShopTotalPricePanel();
        }

    }

    public class RemoveCategoryListener implements ActionListener {

        private final String categoryName;

        public RemoveCategoryListener(String categoryName) {
            this.categoryName = categoryName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.removeCategory(this.categoryName)) {
                setSaveButtonRed();
                refreshShopPanel();
            }
        }

    }

    public class EditProductListener implements ActionListener {

        private final String categoryName;
        private final Product productInfo;
        private final PlainLabel productNameLabel;
        private final PlainLabel productUnitPriceLabel;
        private final PlainLabel productCurrencyLabel;
        private final PlainLabel productUnitSizeLabel;
        private final PlainLabel productUnitTypeLabel;
        private final DarkTextField productNameTextField;
        private final DarkTextField productUnitPriceTextField;
        private final DarkComboBox currencyTypeComboBox;
        private final DarkTextField productUnitSizeTextField;
        private final DarkComboBox unitTypeComboBox;
        private final RoundButton editProductButton;
        private final GreenButton confirmEditionButton;
        private final RedButton cancelEditionButton;

        public EditProductListener(RoundButton editProductButton, DarkPanel editProductButtonPanel, String categoryName, Product productInfo, PlainLabel productNameLabel, DarkTextField productNameTextField, PlainLabel productUnitPriceLabel, DarkTextField productUnitPriceTextField, PlainLabel productCurrencyLabel, DarkComboBox currencyTypeComboBox, PlainLabel productUnitSizeLabel, DarkTextField productUnitSizeTextField, PlainLabel productUnitTypeLabel, DarkComboBox unitTypeComboBox) {
            this.categoryName = categoryName;
            this.productInfo = productInfo;
            this.productNameLabel = productNameLabel;
            this.productNameTextField = productNameTextField;
            this.productUnitPriceLabel = productUnitPriceLabel;
            this.productUnitPriceTextField = productUnitPriceTextField;
            this.productCurrencyLabel = productCurrencyLabel;
            this.currencyTypeComboBox = currencyTypeComboBox;
            this.productUnitSizeLabel = productUnitSizeLabel;
            this.productUnitSizeTextField = productUnitSizeTextField;
            this.productUnitTypeLabel = productUnitTypeLabel;
            this.unitTypeComboBox = unitTypeComboBox;
            this.editProductButton = editProductButton;
            this.confirmEditionButton = new GreenButton(Icons.TICK_ICON, 20, 20);
            this.confirmEditionButton.setForegroundColor(Colors.FOREGROUND_COLOR);
            this.confirmEditionButton.setVisible(false);
            this.confirmEditionButton.addActionListener(new ConfirmEditionListener());
            editProductButtonPanel.add(this.confirmEditionButton);
            this.cancelEditionButton = new RedButton(Icons.CROSS_ICON, 20, 20);
            this.cancelEditionButton.setForegroundColor(Colors.FOREGROUND_COLOR);
            this.cancelEditionButton.setVisible(false);
            this.cancelEditionButton.addActionListener(new CancelEditionListener());
            editProductButtonPanel.add(this.cancelEditionButton);
        }

        private Product createProductInfo() {
            try {
                String newProductName = this.productNameTextField.getText();
                double productUnitPrice = Double.parseDouble(this.productUnitPriceTextField.getText());
                CurrencyType productCurrencyType = CurrencyType.valueOf((String) this.currencyTypeComboBox.getSelectedItem());
                double productUnitSize = Double.parseDouble(this.productUnitSizeTextField.getText());
                UnitType productUnitType = UnitType.valueOf((String) this.unitTypeComboBox.getSelectedItem());
                if (newProductName.isEmpty() || productUnitPrice <= 0 || productUnitSize <= 0) {
                    return null;
                }
                return new Product(newProductName, 1, productUnitPrice, productCurrencyType, productUnitSize, productUnitType);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.hideLabels();
            this.showTextFields();
            this.editProductButton.setVisible(false);
            this.confirmEditionButton.setVisible(true);
            this.cancelEditionButton.setVisible(true);
        }

        public class ConfirmEditionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                Product newProductInfo = createProductInfo();
                if (newProductInfo == null) {
                    return;
                }
                if (model.editProduct(categoryName, productInfo.getProductName(), newProductInfo)) {
                    updateLabelsText();
                    refreshShopTotalPricePanel();
                    hideTextFields();
                    showLabels();
                    cancelEditionButton.setVisible(false);
                    confirmEditionButton.setVisible(false);
                    editProductButton.setVisible(true);
                    setSaveButtonRed();
                }
            }

        }

        public class CancelEditionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                hideTextFields();
                showLabels();
                cancelEditionButton.setVisible(false);
                confirmEditionButton.setVisible(false);
                editProductButton.setVisible(true);
            }

        }

        private void updateLabelsText() {
            this.productNameLabel.setText(this.productNameTextField.getText());
            this.productUnitPriceLabel.setText(this.productUnitPriceTextField.getText());
            this.productCurrencyLabel.setText(String.valueOf(this.currencyTypeComboBox.getSelectedItem()));
            this.productUnitSizeLabel.setText(this.productUnitSizeTextField.getText());
            this.productUnitTypeLabel.setText(String.valueOf(this.unitTypeComboBox.getSelectedItem()));
        }

        private void showLabels() {
            this.productNameLabel.setVisible(true);
            this.productUnitPriceLabel.setVisible(true);
            this.productCurrencyLabel.setVisible(true);
            this.productUnitSizeLabel.setVisible(true);
            this.productUnitTypeLabel.setVisible(true);
        }

        private void hideLabels() {
            this.productNameLabel.setVisible(false);
            this.productUnitPriceLabel.setVisible(false);
            this.productCurrencyLabel.setVisible(false);
            this.productUnitSizeLabel.setVisible(false);
            this.productUnitTypeLabel.setVisible(false);
        }

        private void showTextFields() {
            this.productNameTextField.setText(this.productNameLabel.getText());
            this.productNameTextField.setVisible(true);
            this.productUnitPriceTextField.setText(this.productUnitPriceLabel.getText());
            this.productUnitPriceTextField.setVisible(true);
            this.currencyTypeComboBox.setSelectedItem(this.productCurrencyLabel.getText());
            this.currencyTypeComboBox.setVisible(true);
            this.productUnitSizeTextField.setText(this.productUnitSizeLabel.getText());
            this.productUnitSizeTextField.setVisible(true);
            this.unitTypeComboBox.setSelectedItem(this.productUnitTypeLabel.getText());
            this.unitTypeComboBox.setVisible(true);
        }

        private void hideTextFields() {
            this.productNameTextField.setVisible(false);
            this.productUnitPriceTextField.setVisible(false);
            this.currencyTypeComboBox.setVisible(false);
            this.productUnitSizeTextField.setVisible(false);
            this.unitTypeComboBox.setVisible(false);
        }

    }

    public class IncreaseProductQuantityListener implements ActionListener {

        private final String categoryName;
        private final String productName;
        private final JLabel quantityLabel;

        public IncreaseProductQuantityListener(String categoryName, String productName, JLabel quantityLabel) {
            this.categoryName = categoryName;
            this.productName = productName;
            this.quantityLabel = quantityLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.increaseProductQuantity(this.categoryName, this.productName)) {
                this.quantityLabel.setText(String.valueOf(Integer.parseInt(this.quantityLabel.getText()) + 1));
                this.quantityLabel.revalidate();
                this.quantityLabel.repaint();
                setSaveButtonRed();
                refreshShopTotalPricePanel();
            }
        }

    }

    public class DecreaseProductQuantityListener implements ActionListener {

        private final String categoryName;
        private final String productName;
        private final JLabel quantityLabel;

        public DecreaseProductQuantityListener(String categoryName, String productName, JLabel quantityLabel) {
            this.categoryName = categoryName;
            this.productName = productName;
            this.quantityLabel = quantityLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Integer.parseInt(this.quantityLabel.getText()) == 1) {
                return;
            }
            if (model.decreaseProductQuantity(this.categoryName, this.productName)) {
                this.quantityLabel.setText(String.valueOf(Integer.parseInt(this.quantityLabel.getText()) - 1));
                this.quantityLabel.revalidate();
                this.quantityLabel.repaint();
                setSaveButtonRed();
                refreshShopTotalPricePanel();
            }
        }

    }

    public class RemoveProductListener implements ActionListener {

        private final String categoryName;
        private final String productName;

        public RemoveProductListener(String categoryName, String productName) {
            this.categoryName = categoryName;
            this.productName = productName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.removeProduct(this.categoryName, this.productName)) {
                setSaveButtonRed();
                refreshShopPanel();
            }
        }

    }

    public static class AddProductListener implements ActionListener {

        private final ShopEditController controller;

        public AddProductListener(ShopEditController controller) {
            this.controller = controller;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ShopAddProductModel shopAddProductModel = new ShopAddProductModel();
            ShopAddProductView shopAddProductView = new ShopAddProductView();
            ShopAddProductController shopAddProductController = new ShopAddProductController(shopAddProductModel, shopAddProductView, controller);
            ViewManager.add(shopAddProductView, ViewNames.SHOP_MANAGER_SHOP_ADD_PRODUCT);
            ViewManager.show(ViewNames.SHOP_MANAGER_SHOP_ADD_PRODUCT);
        }

    }

    public class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.clear();
            setSaveButtonRed();
            refreshShopPanel();
        }

    }

    public class ReloadLocalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.loadLocalVersion()) {
                setSaveButtonNormal();
                refreshShopPanel();
            }
        }

    }

    public class SaveLocalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.saveLocalVersion();
            setSaveButtonNormal();
        }

    }

    public class ReloadPublicListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.loadPublicVersion()) {
                refreshShopPanel();
            }
        }

    }

    public class PushToPublicListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.pushChangesToPublicVersion();
        }

    }

    public class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.delete()) {
                ViewManager.show(ViewNames.USER_MENU);
                ViewManager.remove(ViewNames.SHOP_MANAGER_SHOP_CHOOSE);
                ViewManager.remove(ViewNames.SHOP_MANAGER_SHOP_EDIT);
            }
        }

    }

    public class CloseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.close();
            ViewManager.show(ViewNames.SHOP_MANAGER_SHOP_CHOOSE);
            ViewManager.remove(ViewNames.SHOP_MANAGER_SHOP_EDIT);
        }

    }

}
