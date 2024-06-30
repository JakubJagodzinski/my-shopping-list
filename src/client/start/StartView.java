package client.start;

import client.Icons;
import client.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartView extends DarkPanel {

    private final RoundButton signInButton;
    private final RoundButton signUpButton;
    private final RoundButton quitButton;

    public StartView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(ApplicationLogo.getApplicationNameLabel(), gbc);
        gbc.gridy++;

        gbc.insets = new Insets(5, 5, 5, 30);
        centerPanel.add(new JLabel(ApplicationLogo.getImageIcon("appIcon.png")), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(5, 5, 5, 5);

        this.signInButton = new RoundButton("Sign in " + Icons.RIGHTWARDS_ARROW);
        centerPanel.add(this.signInButton, gbc);
        gbc.gridy++;

        this.signUpButton = new RoundButton("Sign up " + Icons.RIGHTWARDS_ARROW);
        centerPanel.add(this.signUpButton, gbc);
        gbc.gridy++;

        this.quitButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Quit");
        centerPanel.add(this.quitButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void addSignInButtonListener(ActionListener e) {
        this.signInButton.addActionListener(e);
    }

    public void addSignUpButtonListener(ActionListener e) {
        this.signUpButton.addActionListener(e);
    }

    public void addQuitButtonListener(ActionListener e) {
        this.quitButton.addActionListener(e);
    }

}
