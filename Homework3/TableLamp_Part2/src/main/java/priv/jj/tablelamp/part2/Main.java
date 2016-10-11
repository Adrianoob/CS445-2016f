import priv.jj.tablelamp.part2.lightbulb.Lightbulb;
import priv.jj.tablelamp.part2.button.Button;

/**
 * Main
 */
public class Main {
    public static void main(String [] args) {
        Button button1 = new Button(new Lightbulb());
        button1.switchOn();
        button1.switchOff();
    }
}
