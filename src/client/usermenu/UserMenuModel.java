package client.usermenu;

import client.ConnectionResources;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class UserMenuModel {

    private String username;
    private final String authorizationToken;

    public UserMenuModel(String username, String authorizationToken) {
        this.username = username;
        this.authorizationToken = authorizationToken;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorizationToken() {
        return this.authorizationToken;
    }

    public void signOut() {
        try {
            ArrayList<String> data = new ArrayList<>();
            data.add(this.username);
            data.add(this.authorizationToken);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SIGN_OUT, data));
        } catch (Exception ignore) {
        }
    }

}
