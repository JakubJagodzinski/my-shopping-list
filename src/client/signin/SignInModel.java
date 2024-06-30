package client.signin;

import client.ConnectionResources;
import client.PasswordEncryptor;
import client.UserSession;
import common.AccountType;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class SignInModel {

    public UserSession signIn(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }
        try {
            ArrayList<String> requestData = new ArrayList<>();
            requestData.add(username);
            requestData.add(PasswordEncryptor.encrypt(password));
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SIGN_IN, requestData));
            ArrayList<String> data = (ArrayList<String>) ConnectionResources.getOis().readObject();
            boolean alreadyLoggedIn = Boolean.parseBoolean(data.get(0));
            if (alreadyLoggedIn) {
                return null;
            }
            boolean authenticated = Boolean.parseBoolean(data.get(1));
            if (authenticated) {
                AccountType accountType = AccountType.valueOf(data.get(2));
                String authenticationToken = data.get(3);
                return new UserSession(username, authenticationToken, accountType);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
