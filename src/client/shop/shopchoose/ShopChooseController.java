package client.shop.shopchoose;

import client.shop.shopcreate.ShopCreateController;
import client.shop.shopcreate.ShopCreateModel;
import client.shop.shopcreate.ShopCreateView;
import client.shop.shopedit.ShopEditController;
import client.shop.shopedit.ShopEditModel;
import client.shop.shopedit.ShopEditView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopChooseController {

    private final ShopChooseModel model;
    private final ShopChooseView view;

    public ShopChooseController(ShopChooseModel model, ShopChooseView view) {
        this.model = model;
        this.view = view;
        this.view.addCreateNewShopButtonListener(new CreateNewShopListener(this));
        this.view.setShopsComboBox(this.model.getShopsNames());
        this.view.addChooseShopButtonListener(new ChooseShopListener());
        this.view.addReturnButtonListener(new ReturnListener());
    }

    public void refreshShopsComboBox() {
        this.view.removeShopsComboBox();
        this.view.setShopsComboBox(this.model.getShopsNames());
    }

    public class CreateNewShopListener implements ActionListener {

        private final ShopChooseController shopChooseController;

        public CreateNewShopListener(ShopChooseController shopChooseController) {
            this.shopChooseController = shopChooseController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ShopCreateModel shopCreateModel = new ShopCreateModel(model.getUsername());
            ShopCreateView shopCreateView = new ShopCreateView();
            ShopCreateController shopCreateController = new ShopCreateController(shopCreateModel, shopCreateView, this.shopChooseController);
            ViewManager.add(shopCreateView, ViewNames.SHOP_MANAGER_SHOP_CREATE);
            ViewManager.show(ViewNames.SHOP_MANAGER_SHOP_CREATE);
        }

    }

    public class ChooseShopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String shopName = view.getSelectedShopName();
            if (shopName != null) {
                ShopEditModel shopEditModel = new ShopEditModel(shopName);
                if (shopEditModel.open(model.getUsername())) {
                    ShopEditView shopEditView = new ShopEditView(shopName);
                    ShopEditController shopEditController = new ShopEditController(shopEditModel, shopEditView);
                    ViewManager.add(shopEditView, ViewNames.SHOP_MANAGER_SHOP_EDIT);
                    ViewManager.show(ViewNames.SHOP_MANAGER_SHOP_EDIT);
                }
            }
        }

    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.USER_MENU);
            ViewManager.remove(ViewNames.SHOP_MANAGER_SHOP_CHOOSE);
        }

    }

}
