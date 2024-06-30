package client.customer.customerlist.customerlistcreate;

import client.customer.customerchooseshop.CustomerChooseShopController;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerListCreateController {

    private final CustomerListCreateModel model;
    private final CustomerListCreateView view;
    private final CustomerChooseShopController controller;

    public CustomerListCreateController(CustomerListCreateModel model, CustomerListCreateView view, CustomerChooseShopController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
        this.view.setShopsComboBox(this.model.getDatabaseShopsNames());
        this.view.addCreateButtonListener(new CreateButtonListener());
        this.view.addCancelButtonListener(new CancelButtonListener());
    }

    public class CreateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String shoppingListName = view.getCustomerListName();
            if (shoppingListName != null && !shoppingListName.isEmpty() && view.getSelectedShopName() != null) {
                if (model.createNewShoppingList(view.getSelectedShopName(), view.getCustomerListName())) {
                    controller.refreshShopsComboBox();
                    ViewManager.show(ViewNames.CUSTOMER_SHOP_CHOOSE);
                    ViewManager.remove(ViewNames.CUSTOMER_LIST_CREATE);
                }
            }
        }
    }

    public class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.CUSTOMER_SHOP_CHOOSE);
            ViewManager.remove(ViewNames.CUSTOMER_LIST_CHOOSE);
        }
    }

}
