package client.components;

import java.awt.*;

public class GreenButton extends RoundButton {

    public GreenButton(String text) {
        super(text);

        setBackgroundColor(Colors.GREEN_BUTTON_COLOR);
        setHoverBackgroundColor(Colors.GREEN_BUTTON_HOVER_COLOR);
        setPressBackgroundColor(Colors.GREEN_BUTTON_PRESS_COLOR);
    }

    public GreenButton(String text, int width, int height) {
        super(text);

        setBackgroundColor(Colors.GREEN_BUTTON_COLOR);
        setHoverBackgroundColor(Colors.GREEN_BUTTON_HOVER_COLOR);
        setPressBackgroundColor(Colors.GREEN_BUTTON_PRESS_COLOR);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

}
