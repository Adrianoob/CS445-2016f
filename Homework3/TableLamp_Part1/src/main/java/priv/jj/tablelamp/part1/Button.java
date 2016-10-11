/**
 * The Button knows how to respond to switchOn() and switchOff().
 */
public class Button {
    private Lightbulb lightbulb;

    public Button(Lightbulb l) {
        lightbulb = l;
    }

    public void switchOn() {
        System.out.println("Button switched to ON");
        lightbulb.on();
    }

    public void switchOff() {
        System.out.println("Button switched to OFF");
        lightbulb.off();
    }
}
