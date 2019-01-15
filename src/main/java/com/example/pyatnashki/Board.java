package com.example.pyatnashki;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private int[][] blocks; // Поле. пустую ячейку будем обозначать нулем.
    private int zeroX;    // Координаты пустой ячейки
    private int zeroY;
    private int h; // мера

    Board(int[][] blocks) {
        this.blocks = deepCopy(blocks);

        h = 0;
        for (int i = 0; i < blocks.length; i++) {  //  в этом цикле определяем координаты нуля и вычисляем h(x)
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != (i * dimension() + j + 1) && blocks[i][j] != 0) {  // если 0 не на своем месте - не считается
                    h += 1;
                }
                if (blocks[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }
    }


    private int dimension() {
        return blocks.length;
    }

    int h() {
        return h;
    }

    boolean isGoal() {  // если все на своем месте, значит это искомая позиция
        return h == 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Board board = (Board) o;

        if (board.dimension() != dimension()) {
            return false;
        }
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != board.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    Iterable<Board> neighbors() {  // все соседние позиции
        // меняем ноль с соседней клеткой, то есть всего 4 варианта
        // если соседнего нет (0 может быть с краю), swap(...) вернет null
        Set<Board> boardList = new HashSet<>();
        boardList.add(swap(getNewBlock(), zeroX, zeroY, zeroX, zeroY + 1));
        boardList.add(swap(getNewBlock(), zeroX, zeroY, zeroX, zeroY - 1));
        boardList.add(swap(getNewBlock(), zeroX, zeroY, zeroX - 1, zeroY));
        boardList.add(swap(getNewBlock(), zeroX, zeroY, zeroX + 1, zeroY));

        return boardList;
    }

    private int[][] getNewBlock() { //  опять же, для неизменяемости
        return deepCopy(blocks);
    }

    private Board swap(int[][] blocks2, int x1, int y1, int x2, int y2) {  //  в этом методе меняем два соседних поля

        if (x2 > -1 && x2 < dimension() && y2 > -1 && y2 < dimension()) {
            int t = blocks2[x2][y2];
            blocks2[x2][y2] = blocks2[x1][y1];
            blocks2[x1][y1] = t;
            return new Board(blocks2);
        } else {
            return null;
        }
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int[] block : blocks) {
            for (int number : block) {
                s.append(String.format("%2d ", number));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = new int[original[i].length];
            if (original[i].length >= 0) {
                System.arraycopy(original[i], 0, result[i], 0, original[i].length);
            }
        }
        return result;
    }
}