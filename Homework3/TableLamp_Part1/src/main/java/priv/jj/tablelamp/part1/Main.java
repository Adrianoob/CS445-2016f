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
