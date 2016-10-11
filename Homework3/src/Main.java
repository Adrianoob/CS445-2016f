/**
 * Main
 */
public class Main {
    public static void main(String [] args) {
        Button button1 = new Button(new Lightbulb());
        button1.getSwitchedOn();
        button1.getSwitchedOff();
    }
}
