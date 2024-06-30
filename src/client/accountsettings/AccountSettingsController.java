package client.accountsettings;

import client.changepassword.ChangePasswordController;
import client.changepassword.ChangePasswordModel;
import client.changepassword.ChangePasswordView;
import client.changeusername.ChangeUsernameController;
import client.changeusername.ChangeUsernameModel;
import client.changeusername.ChangeUsernameView;
import client.confirm.ConfirmController;
import client.confirm.ConfirmModel;
import client.confirm.ConfirmView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class AccountSettingsController {

    private final AccountSettingsModel model;
    private final AccountSettingsView view;

    public AccountSettingsController(AccountSettingsModel model, AccountSettingsView view) {
        this.model = model;
        this.view = view;
        this.view.addChangeUsernameListener(new ChangeUsernameListener(this));
        this.view.addChangePasswordListener(new ChangePasswordListener());
        this.view.addDeleteAccountListener(new DeleteAccountListener());
        this.view.addReturnButtonListener(new ReturnListener());
    }

    public void updateUsername(String newUsername) {
        this.model.setUsername(newUsername);
    }

    public class ChangeUsernameListener implements ActionListener {

        private final AccountSettingsController accountSettingsController;

        public ChangeUsernameListener(AccountSettingsController accountSettingsController) {
            this.accountSettingsController = accountSettingsController;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ChangeUsernameModel changeUsernameModel = new ChangeUsernameModel(model.getUsername(), model.getAuthenticationToken());
            ChangeUsernameView changeUsernameView = new ChangeUsernameView(model.getUsername());
            ChangeUsernameController changeUsernameController = new ChangeUsernameController(changeUsernameModel, changeUsernameView, this.accountSettingsController);
            ViewManager.add(changeUsernameView, ViewNames.CHANGE_USERNAME);
            ViewManager.show(ViewNames.CHANGE_USERNAME);
        }

    }

    public class ChangePasswordListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ChangePasswordModel changePasswordModel = new ChangePasswordModel(model.getUsername());
            ChangePasswordView changePasswordView = new ChangePasswordView();
            ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordModel, changePasswordView);
            ViewManager.add(changePasswordView, ViewNames.CHANGE_PASSWORD);
            ViewManager.show(ViewNames.CHANGE_PASSWORD);
        }

    }

    public class DeleteAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ConfirmModel confirmModel = new ConfirmModel();
            ConfirmView confirmView = new ConfirmView("Are you sure you want to delete \"" + model.getUsername() + "\" customer account?");
            ConfirmController confirmController = new ConfirmController(confirmView, confirmModel, ViewNames.START, ViewNames.ACCOUNT_SETTINGS);
            ViewManager.add(confirmView, ViewNames.CONFIRM);
            ViewManager.show(ViewNames.CONFIRM);
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    CountDownLatch latch = new CountDownLatch(1);
                    confirmModel.addDecisionListener(latch::countDown);
                    latch.await();
                    return null;
                }

                @Override
                protected void done() {
                    if (confirmModel.getDecision()) {
                        if (model.deleteAccount()) {
                            ViewManager.show(ViewNames.START);
                            ViewManager.remove(ViewNames.USER_MENU);
                        }
                    }
                }

            }.execute();
        }

    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.USER_MENU);
            ViewManager.remove(ViewNames.ACCOUNT_SETTINGS);
        }

    }

}
