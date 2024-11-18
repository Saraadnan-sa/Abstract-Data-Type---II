package poet;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Ensure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testSimpleCorpus() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/saraa/Downloads/ps2/ps2/test/poet/simple.txt"));
        String input = "Seek to explore new synergies.";
        String expected = "Seek to explore strange new synergies.";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testCaseInsensitiveCorpus() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/saraa/Downloads/ps2/ps2/test/poet/case-insensitive.txt"));
        String input = "Hello world.";
        String expected = "Hello world.";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testEmptyCorpus() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/saraa/Downloads/ps2/ps2/test/poet/empty.txt"));
        String input = "Seek to explore.";
        String expected = "Seek to explore.";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testNoBridge() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/saraa/Downloads/ps2/ps2/test/poet/no-bridge.txt"));
        String input = "Hello brave new world.";
        String expected = "Hello brave new world.";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testRepeatedWordsCorpus() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/saraa/Downloads/ps2/ps2/test/poet/repeated.txt"));
        String input = "The quick brown fox.";
        String expected = "The very quick brown fox.";
        assertEquals(expected, poet.poem(input));
    }

    @Test
    public void testPunctuationCorpus() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/saraa/Downloads/ps2/ps2/test/poet/punctuation.txt"));
        String input = "Goodbye, cruel world.";
        String expected = "Goodbye, my cruel world.";
        assertEquals(expected, poet.poem(input));
    }
}
