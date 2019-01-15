package com.example.pyatnashki;

public class Main {
    public static void main(String[] args) {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);

        System.out.println("Minimum moves number = " + solver.moves());
        for (Board board : solver.solution())
            System.out.println(board);
    }
}
