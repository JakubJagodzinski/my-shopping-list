package client.signup;

import client.ConnectionResources;
import client.InputValidator;
import client.PasswordEncryptor;
import common.AccountType;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class SignUpModel {

    public boolean signUp(String username, String password, String confirmPassword, AccountType type) {
        try {
            if (!password.equals(confirmPassword) || !InputValidator.isPasswordValid(password) || !InputValidator.isUsernameValid(username)) {
                return false;
            }
            ArrayList<String> data = new ArrayList<>(3);
            data.add(username);
            data.add(PasswordEncryptor.encrypt(password));
            data.add(type.toString());
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CREATE_ACCOUNT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

}
