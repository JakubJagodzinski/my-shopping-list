package client.usermenu;

import client.components.HeaderLabel;

public class UserMenuController {

    private final UserMenuModel model;
    private final HeaderLabel welcomeLabel;

    public UserMenuController(UserMenuModel model) {
        this.model = model;
        this.welcomeLabel = new HeaderLabel("Welcome " + this.model.getUsername() + "!");
    }

    public void updateUsername(String newUsername) {
        this.model.setUsername(newUsername);
        this.welcomeLabel.setText("Welcome " + newUsername + "!");
        this.welcomeLabel.repaint();
    }

    public HeaderLabel getWelcomeLabel() {
        return this.welcomeLabel;
    }

}
