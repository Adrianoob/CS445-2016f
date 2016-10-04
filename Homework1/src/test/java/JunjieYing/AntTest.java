package JunjieYing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Junit test for Ant
 */
public class AntTest {
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
    public void testAnt() {
        Ant a1 = new Ant("Aant");
        assertEquals("Aant Ant", a1.toString());

        a1.move();
        assertEquals("Aant Ant is crawling around.\n", outContent.toString());
    }
}