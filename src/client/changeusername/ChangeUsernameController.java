package client.changeusername;

import client.accountsettings.AccountSettingsController;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeUsernameController {

    private final ChangeUsernameModel model;
    private final ChangeUsernameView view;
    private final AccountSettingsController accountSettingsController;

    public ChangeUsernameController(ChangeUsernameModel model, ChangeUsernameView view, AccountSettingsController accountSettingsController) {
        this.model = model;
        this.view = view;
        this.accountSettingsController = accountSettingsController;
        this.view.addConfirmButtonListener(new ConfirmListener());
        this.view.addCancelButtonListener(new CancelListener());
    }

    public class ConfirmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String newUsername = view.getNewUsername();
            if (model.changeUsername(newUsername)) {
                accountSettingsController.updateUsername(newUsername);
                ViewManager.show(ViewNames.ACCOUNT_SETTINGS);
                ViewManager.remove(ViewNames.CHANGE_USERNAME);
            }
        }

    }

    public class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.ACCOUNT_SETTINGS);
            ViewManager.remove(ViewNames.CHANGE_USERNAME);
        }

    }

}
