package factory;

import button.Button;

public abstract class Dialog {

    public void buttonClick() {
        Button okButton = createButton();
        okButton.onClick();
    }

    /**
     * Subclasses will override this method in order to create specific button
     * objects.
     */
    public abstract Button createButton();
}