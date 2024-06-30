package client.customer.customerlist.customerlistchoose;

import client.customer.customerlist.customerlistedit.CustomerListEditController;
import client.customer.customerlist.customerlistedit.CustomerListEditModel;
import client.customer.customerlist.customerlistedit.CustomerListEditView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerListChooseController {

    private final CustomerListChooseModel model;
    private final CustomerListChooseView view;

    public CustomerListChooseController(CustomerListChooseModel model, CustomerListChooseView view) {
        this.model = model;
        this.view = view;
        this.view.setShoppingListsComboBox(this.model.getShoppingListsNames());
        this.view.addChooseButtonListener(new ChooseShoppingListListener(this));
        this.view.addReturnButtonListener(new ReturnListener());
    }

    public void refreshCustomerListsComboBox() {
        this.view.removeShoppingListComboBox();
        this.view.setShoppingListsComboBox(this.model.getShoppingListsNames());
    }

    public class ChooseShoppingListListener implements ActionListener {

        private final CustomerListChooseController customerListChooseController;

        public ChooseShoppingListListener(CustomerListChooseController customerListChooseController) {
            this.customerListChooseController = customerListChooseController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String shoppingListName = view.getSelectedShoppingListName();
            if (shoppingListName != null) {
                CustomerListEditModel customerListEditModel = new CustomerListEditModel(model.getUsername(), model.getUsername(), model.getShopName(), shoppingListName, true);
                if (customerListEditModel.open()) {
                    CustomerListEditView customerListEditView = new CustomerListEditView(model.getShopName(), shoppingListName, true);
                    CustomerListEditController customerListEditController = new CustomerListEditController(customerListEditModel, customerListEditView, this.customerListChooseController);
                    ViewManager.add(customerListEditView, ViewNames.CUSTOMER_LIST_EDIT);
                    ViewManager.show(ViewNames.CUSTOMER_LIST_EDIT);
                }
            }
        }
    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.CUSTOMER_SHOP_CHOOSE);
            ViewManager.remove(ViewNames.CUSTOMER_LIST_CHOOSE);
        }
    }

}
