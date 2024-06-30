package client;

import common.AccountType;

public class UserSession {

    private String username;
    private final String authorizationToken;
    private final AccountType accountType;

    public UserSession(String username, String authorizationToken, AccountType accountType) {
        this.username = username;
        this.authorizationToken = authorizationToken;
        this.accountType = accountType;
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

    public AccountType getAccountType() {
        return this.accountType;
    }

}
