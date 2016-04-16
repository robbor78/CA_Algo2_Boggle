
public class MyTrie {

    private static final int RADIX = 26;
    private static final int DIM = RADIX * RADIX;

    private Node[] root = new Node[DIM];

    private static class Node {
        private char c; // character
        private Node left, mid, right; // left, middle, and right subtries
        private boolean val; // value associated with string
    }

    public MyTrie() {

    }

    public void put(String key) {

        if (key.length() <= 2) {
            return;
        }

        int x = key.charAt(0);
        int y = key.charAt(1);

        int pos = getPos(x, y);

        Node node = root[pos];
        if (node == null) {
            node = new Node();
            node.c = key.charAt(2);
        }

        put(node, key, 2);
    }

    private Node put(Node x, String key, int d) {
        char c = key.charAt(d);

        if (x == null) {
            x = new Node();
            x.c = c;
        }

        if (c < x.c) {
            x.left = put(x.left, key, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, d + 1);
        } else {
            x.val = true;
        }
        return x;
    }

    private int getPos(int x, int y) {
        return x + y * RADIX;
    }

}
