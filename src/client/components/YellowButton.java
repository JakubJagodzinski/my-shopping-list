package client.components;

import java.awt.*;

public class YellowButton extends RoundButton {

    public YellowButton(String text, int width, int height) {
        super(text);

        setBackgroundColor(Colors.YELLOW_BUTTON_COLOR);
        setHoverBackgroundColor(Colors.YELLOW_BUTTON_HOVER_COLOR);
        setPressBackgroundColor(Colors.YELLOW_BUTTON_PRESS_COLOR);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

}
