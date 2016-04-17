import static org.junit.Assert.*;

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.TST;

public class BoggleSolverTests {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void Test_Algs4() {
        String dictFile = "/run/media/bert/280AC22E0AF59495/coursera/algorithms/2/assignments/5Boggle/boggle/dictionary-algs4.txt";

        String[] boardFiles = {
                "/run/media/bert/280AC22E0AF59495/coursera/algorithms/2/assignments/5Boggle/boggle/board4x4.txt",
                "/run/media/bert/280AC22E0AF59495/coursera/algorithms/2/assignments/5Boggle/boggle/board-q.txt" };

        int[] expectedScores = { 33, 84 };
        int[] expectedWordCount = { 29, 29 };

        int length = boardFiles.length;

        for (int i = 0; i < length; i++) {

            In in = new In(dictFile);
            String[] dictionary = in.readAllStrings();
            BoggleSolver bs = new BoggleSolver(dictionary);

            String boardFile = boardFiles[i];
            BoggleBoard bb = new BoggleBoard(boardFile);

            Iterable<String> iter = bs.getAllValidWords(bb);

            assertWordsInDict(dictionary, iter);

            assertScore(expectedScores[i], bs, iter);

            assertWordCount(expectedWordCount[i], iter);

        }
    }

    @Test
    public void Test_Eye() {
        String dictFile = "/run/media/bert/280AC22E0AF59495/coursera/algorithms/2/assignments/5Boggle/boggle/dictionary-algs4.txt";

        String boardFile = "/run/media/bert/280AC22E0AF59495/coursera/algorithms/2/assignments/5Boggle/boggle/board4x4.txt";

        In in = new In(dictFile);
        String[] dictionary = in.readAllStrings();
        BoggleSolver bs = new BoggleSolver(dictionary);

        BoggleBoard bb = new BoggleBoard(boardFile);
        
        Iterable<String> iter = bs.getAllValidWords(bb);

        
    }

    @Test
    public void StressTest() {
        String dictFile = "/run/media/bert/280AC22E0AF59495/coursera/algorithms/2/assignments/5Boggle/boggle/dictionary-yawl.txt";
        In in = new In(dictFile);
        String[] dictionary = in.readAllStrings();
        BoggleSolver bs = new BoggleSolver(dictionary);

        int max = 1000;
        Stopwatch stopWatch = new Stopwatch();
        for (int i = 0; i < max; i++) {
            BoggleBoard bb = new BoggleBoard();

            bs.getAllValidWords(bb);
        }
        double elapsed = stopWatch.elapsedTime();
        double ave = elapsed / (double) max;
        double bps = 1.0 / ave;
        System.out.println("Elapsed time: " + elapsed + "(sec)");
        System.out.println("Average time: " + ave + "(sec)");
        System.out.println("Boards/sec: " + bps);
    }

    private void assertScore(int expectedScore, BoggleSolver bs,
            Iterable<String> iter) {
        int actualScore = GetScore(bs, iter);
        assertEquals(expectedScore, actualScore);
    }

    private void assertWordsInDict(String[] dictionary, Iterable<String> iter) {
        TST<Boolean> trie = new TST<>();
        for (String word : dictionary) {
            trie.put(word, true);
        }
        for (String word : iter) {
            assertTrue(trie.contains(word));
        }
    }

    private void assertWordCount(int expectedWordCount, Iterable<String> iter) {
        int actualWordCount = CountWords(iter);
        assertEquals(expectedWordCount, actualWordCount);
    }

    private int CountWords(Iterable<String> iter) {
        int i = 0;
        Iterator<String> iterator = iter.iterator();
        for (; iterator.hasNext(); ++i) {
            iterator.next();
        }
        return i;
    }

    private int GetScore(BoggleSolver bs, Iterable<String> iter) {
        int score = 0;
        for (String word : iter) {
            score += bs.scoreOf(word);
        }
        return score;
    }
}
