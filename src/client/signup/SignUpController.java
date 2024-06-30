package client.signup;

import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpController {

    private final SignUpView view;
    private final SignUpModel model;

    public SignUpController(SignUpView view, SignUpModel model) {
        this.view = view;
        this.model = model;
        this.view.addConfirmButtonListener(new ConfirmListener());
        this.view.addReturnButtonListener(new ReturnListener());
    }

    public class ConfirmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.signUp(view.getUsername(), view.getPassword(), view.getConfirmPassword(), view.getAccountType())) {
                ViewManager.show(ViewNames.START);
                ViewManager.remove(ViewNames.SIGN_UP);
            }
        }

    }

    public class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ViewManager.show(ViewNames.START);
            ViewManager.remove(ViewNames.SIGN_UP);
        }

    }

}
