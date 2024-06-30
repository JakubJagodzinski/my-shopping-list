package client.signin;

import client.UserSession;
import client.customer.customermenu.CustomerMenuController;
import client.customer.customermenu.CustomerMenuView;
import client.shopmanagermenu.ShopManagerMenuController;
import client.shopmanagermenu.ShopManagerMenuView;
import client.usermenu.UserMenuModel;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInController {

    private final SignInModel model;
    private final SignInView view;

    public SignInController(SignInModel model, SignInView view) {
        this.model = model;
        this.view = view;
        this.view.addSignInButtonListener(new SignInListener());
        this.view.addReturnButtonListener(new ReturnListener());
    }

    public class SignInListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            UserSession userSession = model.signIn(view.getUsername(), view.getPassword());
            if (userSession != null) {
                UserMenuModel userMenuModel = new UserMenuModel(userSession.getUsername(), userSession.getAuthorizationToken());
                switch (userSession.getAccountType()) {
                    case CUSTOMER:
                        CustomerMenuView customerMenuView = new CustomerMenuView();
                        CustomerMenuController customerMenuController = new CustomerMenuController(userMenuModel, customerMenuView);
                        ViewManager.add(customerMenuView, ViewNames.USER_MENU);
                        break;
                    case SHOP_MANAGER:
                        ShopManagerMenuView shopManagerMenuView = new ShopManagerMenuView();
                        ShopManagerMenuController shopManagerMenuController = new ShopManagerMenuController(userMenuModel, shopManagerMenuView);
                        ViewManager.add(shopManagerMenuView, ViewNames.USER_MENU);
                        break;
                }
                ViewManager.show(ViewNames.USER_MENU);
            }
        }

    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.START);
            ViewManager.remove(ViewNames.SIGN_IN);
        }

    }

}
