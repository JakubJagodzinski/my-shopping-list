package client.shopmanagermenu;

import client.accountsettings.AccountSettingsController;
import client.accountsettings.AccountSettingsModel;
import client.accountsettings.AccountSettingsView;
import client.shop.shopchoose.ShopChooseController;
import client.shop.shopchoose.ShopChooseModel;
import client.shop.shopchoose.ShopChooseView;
import client.usermenu.UserMenuController;
import client.usermenu.UserMenuModel;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopManagerMenuController extends UserMenuController {

    private final UserMenuModel model;
    private final ShopManagerMenuView view;

    public ShopManagerMenuController(UserMenuModel model, ShopManagerMenuView view) {
        super(model);
        this.model = model;
        this.view = view;
        this.view.addMyShopsButtonListener(new MyShopsListener());
        this.view.addAccountSettingsButtonListener(new AccountSettingsListener(this));
        this.view.addSignOutButtonListener(new SignOutListener());
        this.view.setWelcomeLabel(getWelcomeLabel());
    }

    public class MyShopsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ShopChooseModel shopChooseModel = new ShopChooseModel(model.getUsername());
            ShopChooseView shopChooseView = new ShopChooseView();
            ShopChooseController shopChooseController = new ShopChooseController(shopChooseModel, shopChooseView);
            ViewManager.add(shopChooseView, ViewNames.SHOP_MANAGER_SHOP_CHOOSE);
            ViewManager.show(ViewNames.SHOP_MANAGER_SHOP_CHOOSE);
        }
    }

    public class AccountSettingsListener implements ActionListener {

        private final ShopManagerMenuController shopManagerMenuController;

        public AccountSettingsListener(ShopManagerMenuController shopManagerMenuController) {
            this.shopManagerMenuController = shopManagerMenuController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AccountSettingsModel accountSettingsModel = new AccountSettingsModel(model.getUsername(), model.getAuthorizationToken(), this.shopManagerMenuController);
            AccountSettingsView accountSettingsView = new AccountSettingsView();
            AccountSettingsController accountSettingsController = new AccountSettingsController(accountSettingsModel, accountSettingsView);
            ViewManager.add(accountSettingsView, ViewNames.ACCOUNT_SETTINGS);
            ViewManager.show(ViewNames.ACCOUNT_SETTINGS);
        }

    }

    public class SignOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.signOut();
            ViewManager.show(ViewNames.START);
            ViewManager.remove(ViewNames.USER_MENU);
        }

    }

}
