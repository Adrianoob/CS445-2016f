package JunjieYing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Junit test for Thing
 */
public class ThingTest {
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
    public void testToString() {
        Thing t1 = new Thing("some");
        assertEquals("some", t1.toString());

        Thing t2 = new Tiger("tgr");
        assertEquals("tgr Tiger", t2.toString());
    }

}