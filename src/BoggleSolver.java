import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class BoggleSolver {

    private TST<Boolean> trie;
    private int rows;
    private int cols;
    private StringBuilder sb;
    private BoggleBoard board;
    private Set<String> validWords;

    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        trie = new TST<>();
        for (String word : dictionary) {
            trie.put(word, true);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an
    // Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.board = board;
        rows = board.rows();
        cols = board.cols();

        validWords = new HashSet<String>();

        for (int rStart = 0; rStart < rows; rStart++) {
            for (int cStart = 0; cStart < cols; cStart++) {

                sb = new StringBuilder();

                dfs(rStart, cStart);

            }
        }

        return validWords;
    }
    
    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through
    // Z.)
    public int scoreOf(String word) {

        int score = 0;
        if (trie.contains(word)) {
            
        }
        return score;
    }


    private void dfs(int r, int c) {

        sb.append(board.getLetter(r, c));

        String word = sb.toString();
        if (trie.contains(word)) {
            validWords.add(word);
        }

        Iterable<String> iter = trie.keysWithPrefix(word);
        if (iter != null && iter.iterator().hasNext()) {

            boolean isTop = r == 0;
            boolean isBottom = r == rows - 1;
            boolean isLeft = c == 0;
            boolean isRight = c == cols - 1;

            if (!isTop) {
                dfs(r - 1, c);

                if (!isLeft) {
                    dfs(r - 1, c - 1);
                }

                if (!isRight) {
                    dfs(r - 1, c + 1);
                }
            }

            if (!isBottom) {
                dfs(r + 1, c);

                if (!isLeft) {
                    dfs(r + 1, c - 1);
                }

                if (!isRight) {
                    dfs(r + 1, c + 1);
                }
            }

            if (!isLeft) {
                dfs(r, c - 1);
            }

            if (!isRight) {
                dfs(r, c + 1);
            }

        }
        
        sb.deleteCharAt(sb.length()-1);
    }

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
