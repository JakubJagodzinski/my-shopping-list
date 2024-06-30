package client.shop.shopaddproduct;

import client.shop.shopedit.ShopEditController;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.Product;
import common.shoppinglistapi.UnitType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopAddProductController {

    private final ShopAddProductModel model;
    private final ShopAddProductView view;
    private final ShopEditController shopEditController;

    public ShopAddProductController(ShopAddProductModel model, ShopAddProductView view, ShopEditController shopEditController) {
        this.model = model;
        this.view = view;
        this.shopEditController = shopEditController;
        this.view.addReturnButtonListener(new ReturnListener());
        this.view.addAddButtonListener(new AddListener());
    }

    public Product getProductInfo() {
        try {
            String productName = this.view.getProductName();
            int quantity = this.view.getProductQuantity();
            double unitPrice = this.view.getProductUnitPrice();
            CurrencyType currencyType = this.view.getProductCurrencyType();
            double unitSize = this.view.getProductUnitSize();
            UnitType unitType = this.view.getProductUnitType();
            return new Product(productName, quantity, unitPrice, currencyType, unitSize, unitType);
        } catch (Exception e) {
            return null;
        }
    }

    public class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Product productToAdd = getProductInfo();
            if (productToAdd != null) {
                if (model.addProduct(view.getCategoryName(), productToAdd)) {
                    view.clearFields();
                    shopEditController.setSaveButtonRed();
                }
            }
        }

    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            shopEditController.refreshShopPanel();
            ViewManager.show(ViewNames.SHOP_MANAGER_SHOP_EDIT);
            ViewManager.remove(ViewNames.SHOP_MANAGER_SHOP_ADD_PRODUCT);
        }

    }

}
