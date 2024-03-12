package factory;

import button.Button;
import button.HtmlButton;

public class HtmlDialog extends Dialog {

    @Override
    public Button createButton() {
        return new HtmlButton();
    }
}