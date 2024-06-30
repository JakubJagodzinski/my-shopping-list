package client.customer.customermenu;

import client.accountsettings.AccountSettingsController;
import client.accountsettings.AccountSettingsModel;
import client.accountsettings.AccountSettingsView;
import client.customer.customerchooseshop.CustomerChooseShopController;
import client.customer.customerchooseshop.CustomerChooseShopModel;
import client.customer.customerchooseshop.CustomerChooseShopView;
import client.customer.customersharedlists.CustomerSharedListsController;
import client.customer.customersharedlists.CustomerSharedListsModel;
import client.customer.customersharedlists.CustomerSharedListsView;
import client.usermenu.UserMenuController;
import client.usermenu.UserMenuModel;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMenuController extends UserMenuController {

    private final UserMenuModel model;
    private final CustomerMenuView view;

    public CustomerMenuController(UserMenuModel model, CustomerMenuView view) {
        super(model);
        this.model = model;
        this.view = view;
        this.view.addMyShoppingListsListener(new MyShoppingListsListener());
        this.view.addSharedListsListener(new SharedListsListener());
        this.view.addAccountSettingsListener(new AccountSettingsListener(this));
        this.view.addSignOutListener(new SignOutListener());
        this.view.setWelcomeLabel(getWelcomeLabel());
    }

    public void updateUsername(String newUsername) {
        super.updateUsername(newUsername);
        model.setUsername(newUsername);
    }

    public class MyShoppingListsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerChooseShopModel customerChooseShopModel = new CustomerChooseShopModel(model.getUsername());
            CustomerChooseShopView customerChooseShopView = new CustomerChooseShopView();
            CustomerChooseShopController customerChooseShopController = new CustomerChooseShopController(customerChooseShopModel, customerChooseShopView);
            ViewManager.add(customerChooseShopView, ViewNames.CUSTOMER_SHOP_CHOOSE);
            ViewManager.show(ViewNames.CUSTOMER_SHOP_CHOOSE);
        }

    }

    public class SharedListsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerSharedListsModel customerSharedListsModel = new CustomerSharedListsModel(model.getUsername());
            CustomerSharedListsView customerSharedListsView = new CustomerSharedListsView();
            CustomerSharedListsController customerSharedListsController = new CustomerSharedListsController(customerSharedListsModel, customerSharedListsView);
            ViewManager.add(customerSharedListsView, ViewNames.CUSTOMER_SHARED_LISTS);
            ViewManager.show(ViewNames.CUSTOMER_SHARED_LISTS);
        }

    }

    public class AccountSettingsListener implements ActionListener {

        private final CustomerMenuController customerMenuController;

        public AccountSettingsListener(CustomerMenuController customerMenuController) {
            this.customerMenuController = customerMenuController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            AccountSettingsModel accountSettingsModel = new AccountSettingsModel(model.getUsername(), model.getAuthorizationToken(), this.customerMenuController);
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
