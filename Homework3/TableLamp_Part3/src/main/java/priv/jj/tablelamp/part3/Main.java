import priv.jj.tablelamp.part3.lightbulb.Lightbulb;
import priv.jj.tablelamp.part3.button.*;

/**
 * Main
 */
public class Main {
    public static void main(String [] args) {
        Button button = new Button(new Lightbulb());
        button.switchOn();
        button.switchOff();

        PushdownButton pushdownButton = new PushdownButton(new Lightbulb());
        pushdownButton.pushButton();
        pushdownButton.pushButton();
    }
}
