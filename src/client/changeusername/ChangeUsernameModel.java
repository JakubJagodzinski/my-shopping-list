package client.changeusername;

import client.ConnectionResources;
import client.InputValidator;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class ChangeUsernameModel {

    private String username;
    private final String authenticationToken;

    public ChangeUsernameModel(String username, String authenticationToken) {
        this.username = username;
        this.authenticationToken = authenticationToken;
    }

    public boolean changeUsername(String newUsername) {
        if (newUsername.equals(this.username) || !InputValidator.isUsernameValid(newUsername)) {
            return false;
        }
        try {
            ArrayList<String> data = new ArrayList<>();
            data.add(this.username);
            data.add(this.authenticationToken);
            data.add(newUsername);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CHANGE_USERNAME, data));
            if ((boolean) ConnectionResources.getOis().readObject()) {
                this.username = newUsername;
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
