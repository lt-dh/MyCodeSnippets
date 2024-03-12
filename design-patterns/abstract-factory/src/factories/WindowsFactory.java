package factories;

import button.Button;
import button.WindowsButton;
import checkboxes.Checkbox;
import checkboxes.WindowsCheckbox;

public class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}