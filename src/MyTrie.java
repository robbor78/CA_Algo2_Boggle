import edu.princeton.cs.algs4.Queue;

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

        Node node = getRootNode(key, true);

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

        Node root = getRootNode(key, false);

        if (root == null) {
            return false;
        } else {
            Node node = get(root, key, 2);
            return node != null && node.val;
        }
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        char c = key.charAt(d);

        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<String>();
        Node root = getRootNode(prefix, false);
        if (root == null) {
            return queue;
        }
        Node x = get(root, prefix, 0);
        if (x == null) {
            return queue;
        }
        if (x.val) {
            queue.enqueue(prefix);
        }
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) {
            return;
        }
        collect(x.left, prefix, queue);
        if (x.val != null) {
            queue.enqueue(prefix.toString() + x.c);
        }
        collect(x.mid, prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }

    private Node getRootNode(String key, boolean isCreate) {
        int x = (int) key.charAt(0) - (int) 'A';
        int y = (int) key.charAt(1) - (int) 'A';

        int pos = getPos(x, y);

        Node node = root[pos];
        if (isCreate && node == null) {
            node = new Node();
            node.c = key.charAt(2);
            node.val = false;
            root[pos] = node;
        }
        return node;
    }

    private int getPos(int x, int y) {
        return x + y * RADIX;
    }

}
