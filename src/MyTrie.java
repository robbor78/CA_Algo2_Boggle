import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class MyTrie {

    private static final int RADIX = 26;
    private static final int DIM = RADIX * RADIX;

    private Node[] root = new Node[DIM];
    //private boolean isLastPrefixAlsoWord;

    // private String currentKey;

    public class Node {
        private char c; // character
        private String base;
        private Node left, mid, right; // left, middle, and right subtries
        public boolean val; // value associated with string
        private int lastD;
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
            x.base = null;
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

//    public boolean isLastPrefixAlsoWord() {
//        return isLastPrefixAlsoWord;
//    }

    public boolean contains(String key) {

        if (key.length() <= 2) {
            return false;
        }

        Node rootNode = getRootNode(key, false);

        if (rootNode == null) {
            return false;
        } else {
            // currentKey =key;
            Node node = get(rootNode, key, 2);
            return node != null && node.val;
        }
    }

    private Node get(Node x, String currentKey, int d) {
        if (x == null) {
            return null;
        }
        char c = currentKey.charAt(d);

        if (c < x.c) {
            return get(x.left, currentKey, d);
        } else if (c > x.c) {
            return get(x.right, currentKey, d);
        } else if (d < currentKey.length() - 1) {
            return get(x.mid, currentKey, d + 1);
        } else {
            x.lastD = d;
            return x;
        }
    }

    private Node get2(Node node, String currentKey) {
        if (node == null) {
            return null;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(node);

        while (stack.size() > 0) {

            Node x = stack.pop();
            int d = x.lastD;

            char c = currentKey.charAt(d);

            if (c > x.c) {
                // Node tmp = x.right;
                if (x.right != null) {
                    x.right.lastD = d;
                    stack.push(x.right);
                }
            } else if (c < x.c) {
                // Node tmp = x.left;
                if (x.left != null) {
                    x.left.lastD = d;
                    stack.push(x.left);
                }
            } else if (d < currentKey.length() - 1) {
                // Node tmp = x.mid;
                if (x.mid != null) {
                    x.mid.lastD = d + 1;
                    stack.push(x.mid); // d+1
                }
            } else {
                x.lastD = d;
                return x;
            }

        }
        return null;

    }

    public Node isPrefixOrWord(Node startNode, String prefix) {
        Node x;
        // currentKey = prefix;
        if (startNode == null) {
            Node rootNode = getRootNode(prefix, false);
            if (rootNode == null) {
                return null;
            }

            x = get(rootNode, prefix, 2);
            // rootNode.lastD = 2;
            // x = get2(rootNode, prefix);
        } else {
            x = get(startNode, prefix, startNode.lastD);
            // x = get2(startNode, prefix);
        }
        if (x == null) {
            //isLastPrefixAlsoWord= false;
            return null;
        }

        //isLastPrefixAlsoWord = x.val;

        return x;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<String>();

      

        Node rootNode = getRootNode(prefix, false);
        if (rootNode == null) {
            return queue;
        }

       

        

        Node x = get(rootNode, prefix, 2);
        if (x == null) {
            return queue;
        }
//        isLastPrefixAlsoWord = x.val;
//        if (isLastPrefixAlsoWord) {
//            queue.enqueue(prefix);
//        }


        StringBuilder sb;
        if (x.base == null) {
            sb = new StringBuilder(prefix);
        } else {
            sb = new StringBuilder(x.base);
            sb.append(x.c);
        }

        collect(x.mid, sb, queue);
        // }
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) {
            return;
        }
        collect(x.left, prefix, queue);

        if (x.val) {
            queue.enqueue(prefix.toString() + x.c);
        }
        collect(x.mid, prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);

        collect(x.right, prefix, queue);
    }

    private Iterable<Node> getRootRange(String prefix) {
        int start = (int) prefix.charAt(0) - (int) 'A';

        Queue<Node> roots = new Queue<Node>();
        for (int i = 0; i < RADIX; i++) {
            Node x = root[start + i * RADIX];
            if (x != null) {
                roots.enqueue(x);
            }
        }
        return roots;
    }

    private Node getRootNode(String key, boolean isCreate) {
        int x = (int) key.charAt(0) - (int) 'A';
        int y = (int) key.charAt(1) - (int) 'A';

        int pos = getPos(x, y);

        Node node = root[pos];
        if (isCreate && node == null) {
            node = new Node();
            node.c = key.charAt(2);
            node.base = key.substring(0, 2);
            node.val = false;
            root[pos] = node;
        }
        return node;
    }

    private int getPos(int x, int y) {
        return x + y * RADIX;
    }

}
