package client.customer.customerlist.customerlistclone;

import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerListCloneController {

    private final CustomerListCloneModel model;
    private final CustomerListCloneView view;

    public CustomerListCloneController(CustomerListCloneModel model, CustomerListCloneView view) {
        this.model = model;
        this.view = view;
        this.view.addReturnButtonListener(new ReturnListener());
        this.view.addConfirmButtonListener(new ConfirmListener());
    }

    public class ConfirmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.clone(view.getClonedListName())) {
                ViewManager.show(ViewNames.CUSTOMER_LIST_EDIT);
                ViewManager.remove(ViewNames.CUSTOMER_LIST_CLONE);
            }
        }

    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.CUSTOMER_LIST_EDIT);
            ViewManager.remove(ViewNames.CUSTOMER_LIST_CLONE);
        }

    }

}
