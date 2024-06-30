package client.shop.shopcreate;

import client.shop.shopchoose.ShopChooseController;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopCreateController {

    private final ShopCreateModel model;
    private final ShopCreateView view;
    private final ShopChooseController shopChooseController;

    public ShopCreateController(ShopCreateModel model, ShopCreateView view, ShopChooseController shopChooseController) {
        this.model = model;
        this.view = view;
        this.shopChooseController = shopChooseController;
        this.view.addCreateButtonListener(new CreateButtonListener());
        this.view.addCancelButtonListener(new CancelButtonListener());
    }

    public class CreateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String shopName = view.getShopName();
            if (shopName != null && !shopName.isEmpty()) {
                if (model.createNewShop(shopName)) {
                    shopChooseController.refreshShopsComboBox();
                    ViewManager.show(ViewNames.SHOP_MANAGER_SHOP_CHOOSE);
                    ViewManager.remove(ViewNames.SHOP_MANAGER_SHOP_CREATE);
                }
            }
        }
    }

    public class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.SHOP_MANAGER_SHOP_CHOOSE);
            ViewManager.remove(ViewNames.SHOP_MANAGER_SHOP_CREATE);
        }
    }

}
