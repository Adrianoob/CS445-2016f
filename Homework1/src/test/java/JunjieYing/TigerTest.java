package JunjieYing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Junit test for Tiger
 */
public class TigerTest {
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
    public void testTiger() {
        Tiger t1 = new Tiger("tiggg");
        assertEquals("tiggg Tiger", t1.toString());
        t1.whatDidYouEat();
        assertEquals("tiggg Tiger has had nothing to eat!\n", outContent.toString());

        outContent.reset();
        t1.eat(new Thing("food"));
        assertEquals("tiggg Tiger has just eaten a food.\n", outContent.toString());

        outContent.reset();
        t1.whatDidYouEat();
        assertEquals("tiggg Tiger has eaten a food.\n", outContent.toString());
    }

}