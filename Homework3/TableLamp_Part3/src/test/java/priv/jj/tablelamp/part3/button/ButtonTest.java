package priv.jj.tablelamp.part3.button;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import priv.jj.tablelamp.part3.lightbulb.Lightbulb;

/**
 * Created by adrianoob on 10/11/16.
 */
public class ButtonTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    Button button;

    @Before
    public void setUp(){
        System.setOut(new PrintStream(outContent));
        button = new Button(new Lightbulb());
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void switchOn() {
        button.switchOn();
        assertEquals("switched on normally", "Button switched to ON\nLightbulb on\n", outContent.toString());
    }

    @Test
    public void switchOff() {
        button.switchOff();
        assertEquals("switched on normally", "Button switched to OFF\nLightbulb off\n", outContent.toString());
    }

}