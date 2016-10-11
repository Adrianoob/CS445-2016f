/**
 * The lightbulb responds to on() and off() messages.
 */
public class Lightbulb {
    private boolean state = false; // true = on, false = off

    public void getTurnedOn() {
        System.out.println("Lightbulb on");
        state = true;
    }

    public void getTurnedOff() {
        System.out.println("Lightbulb off");
        state = false;
    }
}
