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
        testContains(target,key1);
        testPutContains(target,key2);
        testContains(target,key2);
        testPutContains(target,key3);
        testContains(target,key3);

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
    
    @Test
    public void testNotContains1() {
        MyTrie target = new MyTrie();
        
        testNotContains(target,"HELLO");
    }
    
    @Test
    public void testNotContains2() {
        MyTrie target = new MyTrie();

        String key1="HELLO";
        String key2="WORLD";
        String key3="HELLOWORLD";
        String key4="BLAH";
        
        testPutContains(target,key1);
        testContains(target,key1);
        testNotContains(target,key4);
        testPutContains(target,key2);
        testContains(target,key2);
        testNotContains(target,key4);
        testPutContains(target,key3);
        testContains(target,key3);
        testNotContains(target,key4);

        testContains(target,key1);
        testContains(target,key2);
        testContains(target,key3);
        testNotContains(target,key4);
        
    }

    @Test
    public void testNotContains3() {
        MyTrie target = new MyTrie();

        String key1="HELLO";
        String key2="WORLD";
        String key3="HELLOWORLD";
        String key4="HELLOMARS";
        
        testPutContains(target,key1);
        testContains(target,key1);
        testNotContains(target,key4);
        testPutContains(target,key2);
        testContains(target,key2);
        testNotContains(target,key4);
        testPutContains(target,key3);
        testContains(target,key3);
        testNotContains(target,key4);

        testContains(target,key1);
        testContains(target,key2);
        testContains(target,key3);
        testNotContains(target,key4);
        
    }

    @Test
    public void testNotContains4() {
        MyTrie target = new MyTrie();

        String key1="HELLO";
        String key2="WORLD";
        String key3="HELLOWORLD";
        String key4="HELLOMARS";
        String key5="HELLOJUPITER";
        
        testPutContains(target,key1);
        testContains(target,key1);
        testNotContains(target,key4);
        testNotContains(target,key5);
        testPutContains(target,key2);
        testContains(target,key2);
        testNotContains(target,key4);
        testNotContains(target,key5);
        testPutContains(target,key3);
        testContains(target,key3);
        testNotContains(target,key4);
        testNotContains(target,key5);

        testContains(target,key1);
        testContains(target,key2);
        testContains(target,key3);
        testNotContains(target,key4);
        testNotContains(target,key5);
        
    }
    
    public void testPrefix1() {
        
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

    private void testNotContains(MyTrie target, String key) {
        boolean isContains = target.contains(key);
        assertFalse(isContains);
    }
}
