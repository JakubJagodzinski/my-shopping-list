package client.customer.customerlist.customerlistshare;

import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerListShareController {

    private final CustomerListShareModel model;
    private final CustomerListShareView view;

    public CustomerListShareController(CustomerListShareModel model, CustomerListShareView view) {
        this.model = model;
        this.view = view;
        this.view.addConfirmButtonListener(new ConfirmButtonListener());
        this.view.addReturnButtonListener(new CancelButtonListener());
    }

    public class ConfirmButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.share(view.getUsername(), view.isSharedWithFullAccess())) {
                view.clearUsername();
                view.resetCheckBoxes();
            }
        }

    }

    public class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.CUSTOMER_LIST_EDIT);
            ViewManager.remove(ViewNames.CUSTOMER_LIST_SHARE);
        }

    }

}
