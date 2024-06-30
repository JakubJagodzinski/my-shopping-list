package client.changepassword;

import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordController {

    private final ChangePasswordModel model;
    private final ChangePasswordView view;

    public ChangePasswordController(ChangePasswordModel model, ChangePasswordView view) {
        this.model = model;
        this.view = view;
        this.view.addConfirmButtonListener(new ConfirmListener());
        this.view.addCancelButtonListener(new CancelListener());
    }

    public class ConfirmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.changePassword(view.getPassword(), view.getNewPassword(), view.getConfirmNewPassword())) {
                ViewManager.show(ViewNames.ACCOUNT_SETTINGS);
                ViewManager.remove(ViewNames.CHANGE_PASSWORD);
            }
        }

    }

    public class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.ACCOUNT_SETTINGS);
            ViewManager.remove(ViewNames.CHANGE_PASSWORD);
        }

    }

}
