package server.databaseapi;

public class DatabaseRecord {

    private final String username;
    private final String password;
    private final String accountType;

    public DatabaseRecord(String line) {
        String[] userData = line.split(";");
        this.username = userData[0];
        this.password = userData[1];
        this.accountType = userData[2];
    }

    public DatabaseRecord(String username, String password, String accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getAccountType() {
        return this.accountType;
    }

    @Override
    public String toString() {
        return this.username + ";" + this.password + ";" + this.accountType;
    }

}
