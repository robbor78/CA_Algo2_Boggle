import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

    private static final int WORD_LENGTH_MIN = 3;

    private MyTrie trie;
    // private TST<Boolean> trie;
    private int rows;
    private int cols;
    private int dim;
    private BoggleBoard board;
    private Set<String> validWords;
    private int[] scores = { 0, 0, 0, 1, 1, 2, 3, 5, 11 };

    // private StringBuilder sb;
    // private boolean[] visited;
    // private MyTrie.Node current;

    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        trie = new MyTrie();
        // trie = new TST<>();
        for (String word : dictionary) {
            trie.put(word);
            // trie.put(word,true);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an
    // Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard boardIn) {
        this.board = boardIn;
        rows = board.rows();
        cols = board.cols();
        dim = rows * cols;

        validWords = new HashSet<String>();

        DoDFS d1 = new DoDFS();
        d1.bs = this;
        DoDFS d2 = new DoDFS();
        d2.bs = this;

        Thread t1 = new Thread(d1);
        Thread t2 = new Thread(d2);
        t1.start();
        t2.start();

        for (int rStart = 0; rStart < rows; rStart++) {
            for (int cStart = 0; cStart < cols; cStart++) {

                // sb = new StringBuilder();
                // visited = new boolean[dim];

                // dfs(rStart, cStart);

                while (d1.getIsDo() && d2.getIsDo()) {
                    // try {
                    // Thread.sleep(10);
                    // } catch (InterruptedException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                }
                if (!d1.getIsDo()) {
                    // System.out.println("T1");
                    d1.r = rStart;
                    d1.c = cStart;
                    d1.setIsDo(true);
                } else if (!d2.getIsDo()) {
                    // System.out.println("T2");
                    d2.r = rStart;
                    d2.c = cStart;
                    d2.setIsDo(true);
                }

            }
        }
        
        d1.isCont = false;
        d2.isCont = false;

        return validWords;
    }

    class DoDFS implements Runnable {

        public int r;
        public int c;
        public BoggleSolver bs;

        public boolean isCont = true;
        public Boolean isDo = false;
        
        public boolean getIsDo() {
            boolean result;
            synchronized (isDo) {
                result = isDo;
            }
            return result;
        }

        public void setIsDo(boolean newIsDo) {
            synchronized (isDo) {
                isDo = newIsDo;
            }
        }
        
        public void run() {
            while (isCont) {
                if (getIsDo()) {
                    StringBuilder sb = new StringBuilder();
                    boolean[] visited = new boolean[dim];

                    bs.dfs(r, c, sb, visited, null);
                    setIsDo(false);
                }
            }
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through
    // Z.)
    public int scoreOf(String word) {

        int score = 0;
        if (trie.contains(word)) {
            score = scores[Math.min(word.length(), scores.length - 1)];
        }
        return score;
    }

    private void dfs(int r, int c, StringBuilder sb, boolean[] visited,
            MyTrie.Node current) {

        int pos = r * cols + c;
        if (visited[pos]) {
            return;
        }
        visited[pos] = true;

        char letter = board.getLetter(r, c);
        sb.append(letter);
        if (letter == 'Q') {
            sb.append('U');
        }

        String word = sb.toString();
        boolean isMinLength = word.length() >= WORD_LENGTH_MIN;

        boolean isPrefixOrWord = false;

        MyTrie.Node remember = current;

        if (isMinLength) {

            current = trie.isPrefixOrWord(current, word);
            isPrefixOrWord = current != null;

        }

        if (!isMinLength || (isMinLength && isPrefixOrWord)) {

            if (isMinLength && current.val) {// trie.isLastPrefixAlsoWord()) {
                validWords.add(word);
            }

            boolean isTop = r == 0;
            boolean isBottom = r == rows - 1;
            boolean isLeft = c == 0;
            boolean isRight = c == cols - 1;

            if (!isTop) {
                dfs(r - 1, c, sb, visited, current);

                if (!isLeft) {
                    dfs(r - 1, c - 1, sb, visited, current);
                }

                if (!isRight) {
                    dfs(r - 1, c + 1, sb, visited, current);
                }
            }

            if (!isBottom) {
                dfs(r + 1, c, sb, visited, current);

                if (!isLeft) {
                    dfs(r + 1, c - 1, sb, visited, current);
                }

                if (!isRight) {
                    dfs(r + 1, c + 1, sb, visited, current);
                }
            }

            if (!isLeft) {
                dfs(r, c - 1, sb, visited, current);
            }

            if (!isRight) {
                dfs(r, c + 1, sb, visited, current);
            }

        }

        visited[pos] = false;
        sb.deleteCharAt(sb.length() - 1);
        if (letter == 'Q') {
            sb.deleteCharAt(sb.length() - 1);
        }

        // reset root
        current = remember;

    }

    // private void dfs(int r, int c) {
    //
    // int pos = r * cols + c;
    // if (visited[pos]) {
    // return;
    // }
    // visited[pos] = true;
    //
    // char letter = board.getLetter(r, c);
    // sb.append(letter);
    // if (letter == 'Q') {
    // sb.append('U');
    // }
    //
    // String word = sb.toString();
    // boolean isMinLength = word.length() >= WORD_LENGTH_MIN;
    //
    // boolean isPrefixOrWord = false;
    //
    // MyTrie.Node remember = current;
    //
    // if (isMinLength) {
    //
    // current = trie.isPrefixOrWord(current, word);
    // isPrefixOrWord = current != null;
    //
    // }
    //
    // if (!isMinLength || (isMinLength && isPrefixOrWord)) {
    //
    // if (isMinLength && current.val) {// trie.isLastPrefixAlsoWord()) {
    // validWords.add(word);
    // }
    //
    // boolean isTop = r == 0;
    // boolean isBottom = r == rows - 1;
    // boolean isLeft = c == 0;
    // boolean isRight = c == cols - 1;
    //
    // if (!isTop) {
    // dfs(r - 1, c);
    //
    // if (!isLeft) {
    // dfs(r - 1, c - 1);
    // }
    //
    // if (!isRight) {
    // dfs(r - 1, c + 1);
    // }
    // }
    //
    // if (!isBottom) {
    // dfs(r + 1, c);
    //
    // if (!isLeft) {
    // dfs(r + 1, c - 1);
    // }
    //
    // if (!isRight) {
    // dfs(r + 1, c + 1);
    // }
    // }
    //
    // if (!isLeft) {
    // dfs(r, c - 1);
    // }
    //
    // if (!isRight) {
    // dfs(r, c + 1);
    // }
    //
    // }
    //
    // visited[pos] = false;
    // sb.deleteCharAt(sb.length() - 1);
    // if (letter == 'Q') {
    // sb.deleteCharAt(sb.length() - 1);
    // }
    //
    // // reset root
    // current = remember;
    //
    // }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
