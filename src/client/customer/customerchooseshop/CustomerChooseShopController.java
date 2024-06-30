package client.customer.customerchooseshop;

import client.customer.customerlist.customerlistchoose.CustomerListChooseController;
import client.customer.customerlist.customerlistchoose.CustomerListChooseModel;
import client.customer.customerlist.customerlistchoose.CustomerListChooseView;
import client.customer.customerlist.customerlistcreate.CustomerListCreateController;
import client.customer.customerlist.customerlistcreate.CustomerListCreateModel;
import client.customer.customerlist.customerlistcreate.CustomerListCreateView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerChooseShopController {

    private final CustomerChooseShopModel model;
    private final CustomerChooseShopView view;

    public CustomerChooseShopController(CustomerChooseShopModel model, CustomerChooseShopView view) {
        this.model = model;
        this.view = view;
        this.view.setShopsComboBox(this.model.getCustomerShopsNames());
        this.view.addChooseButtonListener(new ChooseShopListener());
        this.view.addNewShoppingListButtonListener(new CreateNewShoppingListListener(this));
        this.view.addReturnButtonListener(new ReturnListener());
    }

    public void refreshShopsComboBox() {
        this.view.removeShopsComboBox();
        this.view.setShopsComboBox(this.model.getCustomerShopsNames());
    }

    public class CreateNewShoppingListListener implements ActionListener {

        private final CustomerChooseShopController controller;

        public CreateNewShoppingListListener(CustomerChooseShopController controller) {
            this.controller = controller;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerListCreateModel customerListCreateModel = new CustomerListCreateModel(model.getUsername());
            CustomerListCreateView customerListCreateView = new CustomerListCreateView();
            CustomerListCreateController customerListCreateController = new CustomerListCreateController(customerListCreateModel, customerListCreateView, this.controller);
            ViewManager.add(customerListCreateView, ViewNames.CUSTOMER_LIST_CREATE);
            ViewManager.show(ViewNames.CUSTOMER_LIST_CREATE);
        }

    }

    public class ChooseShopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String shopName = view.getSelectedShopName();
            if (shopName != null) {
                CustomerListChooseModel customerListChooseModel = new CustomerListChooseModel(model.getUsername(), shopName);
                CustomerListChooseView customerListChooseView = new CustomerListChooseView(shopName);
                CustomerListChooseController customerListChooseController = new CustomerListChooseController(customerListChooseModel, customerListChooseView);
                ViewManager.add(customerListChooseView, ViewNames.CUSTOMER_LIST_CHOOSE);
                ViewManager.show(ViewNames.CUSTOMER_LIST_CHOOSE);
            }
        }

    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.USER_MENU);
            ViewManager.remove(ViewNames.CUSTOMER_SHOP_CHOOSE);
        }

    }

}
