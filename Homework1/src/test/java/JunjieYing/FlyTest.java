package JunjieYing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Junit test for Fly
 */
public class FlyTest {
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
    public void testFly() {
        Fly f = new Fly("Flyy");
        assertEquals("Flyy Fly", f.toString());

        f.move();
        assertEquals("Flyy Fly is buzzing around in flight.\n", outContent.toString());

        outContent.reset();
        f.eat(new Thing("some"));
        assertEquals("Flyy Fly has just eaten a some.\n", outContent.toString());

        outContent.reset();
        f.whatDidYouEat();
        assertEquals("Flyy Fly has eaten a some.\n", outContent.toString());

        outContent.reset();
        f.eat(new Tiger("poor"));
        assertEquals("Flyy Fly won't eat a poor Tiger.\n", outContent.toString());
    }
}