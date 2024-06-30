package client.customer.customerlist.customerlistedit;

import client.components.*;
import client.customer.customerlist.customerlistclone.CustomerListCloneController;
import client.customer.customerlist.customerlistclone.CustomerListCloneModel;
import client.customer.customerlist.customerlistclone.CustomerListCloneView;
import client.customer.customerlist.customerlistshare.CustomerListShareController;
import client.customer.customerlist.customerlistshare.CustomerListShareModel;
import client.customer.customerlist.customerlistshare.CustomerListShareView;
import client.customer.customerlist.customerlistaddproduct.CustomerListAddProductController;
import client.customer.customerlist.customerlistaddproduct.CustomerListAddProductModel;
import client.customer.customerlist.customerlistaddproduct.CustomerListAddProductView;
import client.customer.customerlist.customerlistchoose.CustomerListChooseController;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;
import client.Icons;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.Product;
import common.shoppinglistapi.UnitType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerListEditController {

    private final CustomerListEditModel model;
    private final CustomerListEditView view;
    private final CustomerListChooseController customerListChooseController;
    private Thread listRefreshThread;

    public CustomerListEditController(CustomerListEditModel model, CustomerListEditView view, CustomerListChooseController customerListChooseController) {
        this.model = model;
        this.view = view;
        this.customerListChooseController = customerListChooseController;
        AddProductListener.setShoppingListEditController(this);
        if (!this.model.hasFullAccess()) {
            this.view.disableButtonsForReadOnlyMode();
        } else {
            if (!this.model.getOwnerName().equals(this.model.getEditorName())) {
                this.view.disableButtonsForFullAccessMode();
            }
            this.view.addAddProductButtonListener(new AddProductListener());
            this.view.addClearButtonListener(new ClearListener());
            this.view.addSaveButtonListener(new SaveListener());
            this.view.addReloadButtonListener(new ReloadListener());
            this.view.addShareButtonListener(new ShareListener());
            this.view.addDeleteButtonListener(new DeleteListener());
        }
        this.view.addRefreshButtonListener(new RefreshListener());
        this.view.addCloneButtonListener(new CloneListener());
        this.view.addReturnButtonListener(new ReturnListener());
        this.view.setShoppingListPane(this.createShoppingListPane());
        this.view.setListTotalPriceLabel(this.createListTotalPriceLabel());
        if (this.model.hasFullAccess()) {
            this.view.setListCurrencyComboBox(this.createListCurrencyComboBox());
            this.view.addListCurrencyComboBoxListener(new CurrencyComboBoxListener());
        }
        this.startListRefreshThread();
    }

    private void startListRefreshThread() {
        this.listRefreshThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(15_000);
                    this.model.removeUnavailableProducts();
                    this.refreshShoppingListPanel();
                } catch (Exception e) {
                    if (!this.listRefreshThread.isInterrupted()) {
                        return;
                    }
                }
            }
        });
        this.listRefreshThread.start();
    }

    private void closeListRefreshThread() {
        this.listRefreshThread.interrupt();
    }

    public synchronized void refreshShoppingListPanel() {
        this.view.setShoppingListPane(this.createShoppingListPane());
        this.refreshListTotalPricePanel();
    }

    public synchronized void refreshListTotalPricePanel() {
        this.view.setListTotalPriceLabel(this.createListTotalPriceLabel());
        if (this.model.hasFullAccess()) {
            DarkComboBox listCurrencyComboBox = this.view.getListCurrencyComboBox();
            listCurrencyComboBox.setSelectedItem(this.model.getCurrencyType().toString());
            this.view.setListCurrencyComboBox(listCurrencyComboBox);
        }
    }

    public void setSaveButtonRed() {
        this.view.setSaveButtonColorRed();
    }

    public void setSaveButtonNormal() {
        this.view.setSaveButtonColorNormal();
    }

    public BoldLabel createListTotalPriceLabel() {
        if (this.model.hasFullAccess()) {
            return new BoldLabel("Total price: " + this.model.getTotalPrice());
        } else {
            return new BoldLabel("Total price: " + this.model.getTotalPrice() + " " + this.model.getCurrencyType());
        }
    }

    public DarkComboBox createListCurrencyComboBox() {
        DarkComboBox listCurrencyComboBox = new DarkComboBox(CurrencyType.getValues());
        listCurrencyComboBox.setSelectedItem(this.model.getCurrencyType());
        return listCurrencyComboBox;
    }

    public JScrollPane createShoppingListPane() {
        ArrayList<String> categoriesNames = this.model.getCategoriesNames();

        DarkPanel shoppingListPanel = new DarkPanel();
        shoppingListPanel.setLayout(new BoxLayout(shoppingListPanel, BoxLayout.Y_AXIS));

        if (categoriesNames.isEmpty()) {
            shoppingListPanel.add(this.createListIsEmptyPanel());
        } else {
            for (var categoryName : categoriesNames) {
                shoppingListPanel.add(this.createCategoryPanel(categoryName));
            }
        }

        JScrollPane shoppingListPane = new JScrollPane(shoppingListPanel);
        shoppingListPane.setBackground(Colors.BACKGROUND_COLOR);
        shoppingListPane.setForeground(Colors.FOREGROUND_COLOR);
        shoppingListPane.setPreferredSize(new Dimension(1600, 750));

        JScrollBar verticalScrollBar = shoppingListPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(10);

        return shoppingListPane;
    }

    public GridPanel createListIsEmptyPanel() {
        GridPanel listIsEmptyPanel = new GridPanel(2, 1);
        listIsEmptyPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        listIsEmptyPanel.add(new HeaderLabel("Oops! It seems that your shopping list is empty..."), gbc);
        gbc.gridy++;

        listIsEmptyPanel.add(new JLabel(ApplicationLogo.getImageIcon("emptyListIcon.png")), gbc);

        return listIsEmptyPanel;
    }

    public DarkPanel createCategoryPanel(String categoryName) {
        ArrayList<Product> products = this.model.getProducts(categoryName);

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

        if (this.model.hasFullAccess()) {
            deleteCategoryButtonPanel.add(createDeleteCategoryButton(categoryName));
        }

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
        GridPanel productsInfoHeader = new GridPanel(1, 7);
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

        if (this.model.hasFullAccess()) {
            DarkPanel remove = new DarkPanel();
            remove.add(new BoldLabel("remove"));
            productsInfoHeader.add(remove);
        }

        return productsInfoHeader;
    }

    public GridPanel createProductPanel(String categoryName, Product product) {
        GridPanel productPanel = new GridPanel(1, 7);
        productPanel.setBorder(new LineBorder(Color.DARK_GRAY));

        DarkPanel productQuantity = new DarkPanel();

        PlainLabel quantityLabel = new PlainLabel(String.valueOf(product.getQuantity()));

        if (this.model.hasFullAccess()) {
            RedButton decreaseQuantityButton = new RedButton(Icons.MINUS_SIGN_ICON, 20, 20);
            decreaseQuantityButton.setForegroundColor(Colors.FOREGROUND_COLOR);
            decreaseQuantityButton.addActionListener(new DecreaseProductQuantityListener(categoryName, product.getProductName(), quantityLabel));
            productQuantity.add(decreaseQuantityButton);
        }

        productQuantity.add(quantityLabel);

        if (this.model.hasFullAccess()) {
            GreenButton increaseQuantityButton = new GreenButton(Icons.PLUS_SIGN_ICON, 20, 20);
            increaseQuantityButton.setForegroundColor(Colors.FOREGROUND_COLOR);
            increaseQuantityButton.addActionListener(new IncreaseProductQuantityListener(categoryName, product.getProductName(), quantityLabel));
            productQuantity.add(increaseQuantityButton);
        }

        productPanel.add(productQuantity);

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

        if (this.model.hasFullAccess()) {
            DarkPanel deleteProductButtonPanel = new DarkPanel();

            RedButton deleteProductButton = new RedButton(Icons.CROSS_ICON, 20, 20);
            deleteProductButton.setForegroundColor(Colors.FOREGROUND_COLOR);
            deleteProductButton.addActionListener(new RemoveProductListener(categoryName, product.getProductName()));

            deleteProductButtonPanel.add(deleteProductButton);

            productPanel.add(deleteProductButtonPanel);
        }

        return productPanel;
    }

    public class CurrencyComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.changeCurrencyType(view.getSelectedCurrencyType());
            refreshListTotalPricePanel();
        }

    }

    public class RemoveCategoryListener implements ActionListener {

        private final String categoryName;

        public RemoveCategoryListener(String categoryName) {
            this.categoryName = categoryName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.removeCategory(categoryName)) {
                setSaveButtonRed();
            }
            refreshShoppingListPanel();
        }

    }

    public class ReloadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.reload();
            setSaveButtonNormal();
            refreshShoppingListPanel();
        }

    }

    public class AddProductListener implements ActionListener {

        private static CustomerListEditController customerListEditController;

        public static void setShoppingListEditController(CustomerListEditController controller) {
            customerListEditController = controller;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerListAddProductModel customerListAddProductModel = new CustomerListAddProductModel(model.getShopName());
            CustomerListAddProductView customerListAddProductView = new CustomerListAddProductView();
            CustomerListAddProductController customerListAddProductController = new CustomerListAddProductController(customerListAddProductModel, customerListAddProductView, customerListEditController);
            ViewManager.add(customerListAddProductView, ViewNames.CUSTOMER_LIST_ADD_PRODUCT);
            ViewManager.show(ViewNames.CUSTOMER_LIST_ADD_PRODUCT);
        }

    }

    public class IncreaseProductQuantityListener implements ActionListener {

        private final String categoryName;
        private final String productName;
        private final PlainLabel quantityLabel;

        public IncreaseProductQuantityListener(String categoryName, String productName, PlainLabel quantityLabel) {
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
                refreshListTotalPricePanel();
            } else {
                refreshShoppingListPanel();
            }
        }

    }

    public class DecreaseProductQuantityListener implements ActionListener {

        private final String categoryName;
        private final String productName;
        private final PlainLabel quantityLabel;

        public DecreaseProductQuantityListener(String categoryName, String productName, PlainLabel quantityLabel) {
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
                refreshListTotalPricePanel();
            } else {
                refreshShoppingListPanel();
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
            }
            refreshShoppingListPanel();
        }

    }

    public class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.clear();
            setSaveButtonRed();
            refreshShoppingListPanel();
        }

    }

    public class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.save();
            setSaveButtonNormal();
        }

    }

    public class CloneListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerListCloneModel customerListCloneModel = new CustomerListCloneModel(model.getCustomerListName(), model.getEditorName());
            CustomerListCloneView customerListCloneView = new CustomerListCloneView();
            CustomerListCloneController customerListCloneController = new CustomerListCloneController(customerListCloneModel, customerListCloneView);
            ViewManager.add(customerListCloneView, ViewNames.CUSTOMER_LIST_CLONE);
            ViewManager.show(ViewNames.CUSTOMER_LIST_CLONE);
        }

    }

    public class ShareListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.getOwnerName().equals(model.getEditorName())) {
                CustomerListShareModel customerListShareModel = new CustomerListShareModel(model.getOwnerName());
                CustomerListShareView customerListShareView = new CustomerListShareView();
                CustomerListShareController customerListShareController = new CustomerListShareController(customerListShareModel, customerListShareView);
                ViewManager.add(customerListShareView, ViewNames.CUSTOMER_LIST_SHARE);
                ViewManager.show(ViewNames.CUSTOMER_LIST_SHARE);
            }
        }

    }

    public class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.delete()) {
                ViewManager.show(ViewNames.USER_MENU);
                ViewManager.remove(ViewNames.CUSTOMER_LIST_EDIT);
                ViewManager.remove(ViewNames.CUSTOMER_LIST_CHOOSE);
                ViewManager.remove(ViewNames.CUSTOMER_SHOP_CHOOSE);
            }
        }

    }

    public class RefreshListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            refreshShoppingListPanel();
        }

    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            closeListRefreshThread();
            model.close();
            if (model.getOwnerName().equals(model.getEditorName())) {
                customerListChooseController.refreshCustomerListsComboBox();
                ViewManager.show(ViewNames.CUSTOMER_LIST_CHOOSE);
            } else {
                ViewManager.show(ViewNames.CUSTOMER_SHARED_LISTS);
            }
            ViewManager.remove(ViewNames.CUSTOMER_LIST_EDIT);
        }

    }

}
