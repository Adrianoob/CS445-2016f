/**
 * The Button knows how to respond to switchOn() and switchOff().
 */
public class Button {
    private boolean state = false; // true = on, false = off
    private Lightbulb lightbulb;

    public Button(Lightbulb l) {
        lightbulb = l;
    }

    public void getSwitchedOn() {
        System.out.println("Button switched to ON");
        state = true;
        lightbulb.getTurnedOn();
    }

    public void getSwitchedOff() {
        System.out.println("Button switched to OFF");
        state = false;
        lightbulb.getTurnedOff();
    }
}
