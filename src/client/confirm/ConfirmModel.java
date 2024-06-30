package client.confirm;

public class ConfirmModel {

    private boolean decision;
    private Runnable decisionListener;

    public synchronized boolean getDecision() {
        return this.decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
        if (this.decisionListener != null) {
            this.decisionListener.run();
        }
    }

    public synchronized void addDecisionListener(Runnable listener) {
        this.decisionListener = listener;
    }

}
