package JunjieYing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Junit test for Bat
 */
public class BatTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp(){
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testBat() {
        Bat b1 = new Bat("Baat");
        assertEquals("Baat Bat", b1.toString());

        b1.move();
        assertEquals("Baat Bat is swooping through the dark.\n", outContent.toString());

        outContent.reset();
        b1.eat(new Thing("some"));
        assertEquals("Baat Bat won't eat a some.\n", outContent.toString());

        outContent.reset();
        b1.eat(new Tiger("poor"));
        assertEquals("Baat Bat has just eaten a poor Tiger.\n", outContent.toString());

    }

}