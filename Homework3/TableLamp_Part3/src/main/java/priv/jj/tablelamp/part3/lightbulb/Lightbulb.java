package priv.jj.tablelamp.part3.lightbulb;

/**
 * The lightbulb responds to on() and off() messages.
 */
public class Lightbulb {
    private boolean state = false; // true = on, false = off

    public void on() {
        System.out.println("Lightbulb on");
        state = true;
    }

    public void off() {
        System.out.println("Lightbulb off");
        state = false;
    }
}
