import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyTrieTests {

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
    public void testPutContains1() {
        MyTrie target = new MyTrie();
        
        testPutContains(target,"HELLO");
    }

    @Test
    public void testPutContains2() {
        MyTrie target = new MyTrie();

        String key1="HELLO";
        String key2="WORLD";
        String key3="HELLOWORLD";
        
        testPutContains(target,key1);
        testPutContains(target,key2);
        testPutContains(target,key3);

        testContains(target,key1);
        testContains(target,key2);
        testContains(target,key3);
        
    }
    
    @Test
    public void testPutTwiceContains() {
        MyTrie target = new MyTrie();

        String key1="HELLO";
        String key2="WORLD";
        String key3="HELLOWORLD";
        
        testPutContains(target,key1);
        testPutContains(target,key2);
        testPutContains(target,key3);

        testPutContains(target,key1);
        testPutContains(target,key2);
        testPutContains(target,key3);

        testContains(target,key1);
        testContains(target,key2);
        testContains(target,key3);
        
    }
    
    private void testPutContains(MyTrie target, String key) {
        target.put(key);
        boolean isContains = target.contains(key);
        assertTrue(isContains);
    }

    private void testContains(MyTrie target, String key) {
        boolean isContains = target.contains(key);
        assertTrue(isContains);
    }

}
