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

        Node node = getRootNode(key);

        put(node, key, 2);
    }

    private Node put(Node x, String key, int d) {
        char c = key.charAt(d);

        if (x == null) {
            x = new Node();
            x.c = c;
            x.val = false;
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

    public boolean contains(String key) {
        if (key.length() <= 2) {
            return false;
        }

        Node root = getRootNode(key);

        if (root == null) {
            return false;
        } else {
            return get(root, key, 2);
        }
    }

    private boolean get(Node x, String key, int d) {
        if (x == null) {
            return false;
        }
        char c = key.charAt(d);
        
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x.val;
        }
    }

    private Node getRootNode(String key) {
        int x = key.charAt(0);
        int y = key.charAt(1);

        int pos = getPos(x, y);

        Node node = root[pos];
        return node;
    }

    private int getPos(int x, int y) {
        return x + y * RADIX;
    }

}
