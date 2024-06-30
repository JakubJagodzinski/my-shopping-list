package server.loggedinuserslist;

import java.util.concurrent.ConcurrentHashMap;

public class LoggedInUsersList {

    private final ConcurrentHashMap<String, UserData> users;

    public LoggedInUsersList() {
        this.users = new ConcurrentHashMap<>();
    }

    public void print() {
        if (this.users.isEmpty()) {
            System.out.println("There are no logged in users.");
            return;
        }
        System.out.println("Logged in users:");
        System.out.println("[username] / [account type]");
        for (var user : this.users.entrySet()) {
            System.out.println(user.getKey() + " / " + user.getValue().accountType());
        }
        System.out.println("Total logged in users: " + this.users.size());
    }

    public void changeUsername(String from, String to) {
        UserData userData = this.users.get(from);
        this.users.put(to, new UserData(to, userData.accountType(), userData.authenticationToken()));
        this.users.remove(from);
    }

    public boolean checkAuthenticationToken(String username, String authenticationToken) {
        return this.isUserLoggedIn(username) && this.users.get(username).authenticationToken().equals(authenticationToken);
    }

    public boolean isUserLoggedIn(String username) {
        return this.users.containsKey(username);
    }

    public void addUser(UserData userData) {
        this.users.put(userData.username(), userData);
    }

    public void removeUser(String username) {
        this.users.remove(username);
    }

}
