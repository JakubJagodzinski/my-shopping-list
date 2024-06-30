package client.changepassword;

import client.ConnectionResources;
import client.InputValidator;
import client.PasswordEncryptor;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class ChangePasswordModel {

    private final String username;

    public ChangePasswordModel(String username) {
        this.username = username;
    }

    public boolean changePassword(String password, String newPassword, String confirmNewPassword) {
        try {
            if (!newPassword.equals(confirmNewPassword) || newPassword.equals(password) || !InputValidator.isPasswordValid(newPassword)) {
                return false;
            }
            ArrayList<String> data = new ArrayList<>();
            data.add(this.username);
            data.add(PasswordEncryptor.encrypt(password));
            data.add(PasswordEncryptor.encrypt(newPassword));
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CHANGE_PASSWORD, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

}
