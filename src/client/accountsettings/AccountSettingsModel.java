package client.accountsettings;

import client.ConnectionResources;
import client.usermenu.UserMenuController;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class AccountSettingsModel {

    private String username;
    private final String authenticationToken;
    private final UserMenuController userMenuController;

    public AccountSettingsModel(String username, String authenticationToken, UserMenuController userMenuController) {
        this.username = username;
        this.authenticationToken = authenticationToken;
        this.userMenuController = userMenuController;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.userMenuController.updateUsername(username);
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public boolean deleteAccount() {
        try {
            ArrayList<String> data = new ArrayList<>();
            data.add(this.username);
            data.add(this.authenticationToken);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.DELETE_ACCOUNT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

}
