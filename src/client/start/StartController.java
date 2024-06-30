package client.start;

import client.connect.ConnectModel;
import client.signin.SignInController;
import client.signin.SignInModel;
import client.signin.SignInView;
import client.signup.SignUpController;
import client.signup.SignUpModel;
import client.signup.SignUpView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartController {

    private final StartView view;

    public StartController(StartView view) {
        this.view = view;
        this.view.addSignInButtonListener(new SignInListener());
        this.view.addSignUpButtonListener(new SignUpListener());
        this.view.addQuitButtonListener(new QuitListener());
    }

    public static class SignInListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SignInModel signInModel = new SignInModel();
            SignInView signInView = new SignInView();
            SignInController signInController = new SignInController(signInModel, signInView);
            ViewManager.add(signInView, ViewNames.SIGN_IN);
            ViewManager.show(ViewNames.SIGN_IN);
        }

    }

    public static class SignUpListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SignUpView signUpView = new SignUpView();
            SignUpModel signUpModel = new SignUpModel();
            SignUpController signUpController = new SignUpController(signUpView, signUpModel);
            ViewManager.add(signUpView, ViewNames.SIGN_UP);
            ViewManager.show(ViewNames.SIGN_UP);
        }

    }

    public static class QuitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ConnectModel.disconnect();
            System.exit(0);
        }

    }

}
