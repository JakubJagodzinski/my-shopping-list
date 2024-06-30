package client.signin;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class SignInView extends DarkPanel {

    private final DarkTextField usernameField;
    private final DarkPasswordField passwordField;
    private final RoundButton signInButton;
    private final RoundButton returnButton;

    public SignInView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Sign in"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Username:"), gbc);
        gbc.gridy++;

        this.usernameField = new DarkTextField(15);
        centerPanel.add(this.usernameField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Password:"), gbc);
        gbc.gridy++;

        this.passwordField = new DarkPasswordField(15);
        centerPanel.add(this.passwordField, gbc);
        gbc.gridy++;

        this.signInButton = new RoundButton("Sign in " + Icons.RIGHTWARDS_ARROW);
        centerPanel.add(this.signInButton, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);
        gbc.gridy++;

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public String getUsername() {
        return this.usernameField.getText().strip();
    }

    public String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }

    public void addSignInButtonListener(ActionListener e) {
        this.signInButton.addActionListener(e);
    }

    public void addReturnButtonListener(ActionListener e) {
        this.returnButton.addActionListener(e);
    }

}
