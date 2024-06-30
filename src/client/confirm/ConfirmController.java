package client.confirm;

import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmController {

    private final ConfirmModel model;
    private final ConfirmView view;
    private final ViewNames yesDecisionViewName;
    private final ViewNames noDecisionViewName;

    public ConfirmController(ConfirmView view, ConfirmModel model, ViewNames yesDecisionViewName, ViewNames noDecisionViewName) {
        this.model = model;
        this.view = view;
        this.yesDecisionViewName = yesDecisionViewName;
        this.noDecisionViewName = noDecisionViewName;
        this.view.addYesButtonListener(new YesListener());
        this.view.addNoButtonListener(new NoListener());
    }

    public class YesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDecision(true);
            ViewManager.show(yesDecisionViewName);
            ViewManager.remove(ViewNames.CONFIRM);
        }

    }

    public class NoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.setDecision(false);
            ViewManager.show(noDecisionViewName);
            ViewManager.remove(ViewNames.CONFIRM);
        }

    }

}
