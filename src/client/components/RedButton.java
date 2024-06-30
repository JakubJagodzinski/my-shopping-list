package client.components;

import java.awt.*;

public class RedButton extends RoundButton {

    public RedButton(String text) {
        super(text);

        setBackgroundColor(Colors.RED_BUTTON_COLOR);
        setHoverBackgroundColor(Colors.RED_BUTTON_HOVER_COLOR);
        setPressBackgroundColor(Colors.RED_BUTTON_PRESS_COLOR);
    }

    public RedButton(String text, int width, int height) {
        super(text);

        setBackgroundColor(Colors.RED_BUTTON_COLOR);
        setHoverBackgroundColor(Colors.RED_BUTTON_HOVER_COLOR);
        setPressBackgroundColor(Colors.RED_BUTTON_PRESS_COLOR);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

}
