package priv.jj.tablelamp.part3.button;

import priv.jj.tablelamp.part3.lightbulb.Lightbulb;

/**
 * Created by adrianoob on 10/11/16.
 */
public class PushdownButton implements ButtonInterface {
    private Lightbulb lightbulb;
    private boolean isOn; // true = on, false = off;

    public PushdownButton(Lightbulb l) {
        lightbulb = l;
        isOn = false;
    }

    public void pushButton() {
        if (isOn) {
            switchOff();
            isOn = false;
        }
        else {
            switchOn();
            isOn = true;
        }
    }

    public void switchOn() {
        System.out.println("Button switched to ON");
        lightbulb.on();
        isOn = true;
    }

    public void switchOff() {
        System.out.println("Button switched to OFF");
        lightbulb.off();
        isOn = false;
    }
}
