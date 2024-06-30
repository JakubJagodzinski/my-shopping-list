package client.customer.customersharedlists;

import client.customer.customerlist.customerlistedit.CustomerListEditController;
import client.customer.customerlist.customerlistedit.CustomerListEditModel;
import client.customer.customerlist.customerlistedit.CustomerListEditView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerSharedListsController {

    private final CustomerSharedListsModel model;
    private final CustomerSharedListsView view;

    public CustomerSharedListsController(CustomerSharedListsModel model, CustomerSharedListsView view) {
        this.model = model;
        this.view = view;
        this.view.setSharedListsPathsComboBox(model.getSharedLists());
        this.view.addChooseButtonListener(new ChooseListener());
        this.view.addReturnButtonListener(new ReturnListener());
    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.USER_MENU);
            ViewManager.remove(ViewNames.CUSTOMER_SHARED_LISTS);
        }
    }

    public class ChooseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String sharedListPath = view.getSelectedSharedListPath();
            if (sharedListPath != null) {
                CustomerListInfo customerListInfo = new CustomerListInfo(sharedListPath);
                CustomerListEditModel customerListEditModel = new CustomerListEditModel(customerListInfo.getOwnerName(), model.getUsername(), customerListInfo.getShopName(), customerListInfo.getCustomerListName(), customerListInfo.hasFullAccess());
                if (customerListEditModel.open()) {
                    CustomerListEditView customerListEditView = new CustomerListEditView(customerListInfo.getShopName(), customerListInfo.getCustomerListName(), customerListInfo.hasFullAccess());
                    CustomerListEditController customerListEditController = new CustomerListEditController(customerListEditModel, customerListEditView, null);
                    ViewManager.add(customerListEditView, ViewNames.CUSTOMER_LIST_EDIT);
                    ViewManager.show(ViewNames.CUSTOMER_LIST_EDIT);
                }
            }
        }
    }

}
