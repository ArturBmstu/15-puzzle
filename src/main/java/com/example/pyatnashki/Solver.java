package com.example.pyatnashki;

import java.util.*;

class Solver {

    private List<Board> result = new ArrayList<>();   // этот лист - цепочка ходов, приводящих к решению задачи

    private class Item {    // Чтобы узнать длину пути, нам нужно помнить предыдущие позиции (и не только поэтому)
        private Item prevBoard;  // ссылка на предыдущий
        private Board board;   // сама позиция

        private Item(Item prevBoard, Board board) {
            this.prevBoard = prevBoard;
            this.board = board;
        }

        Board getBoard() {
            return board;
        }
    }

    Solver(Board initial) {
        if (!isSolvable()) {
            return;
        }

        // очередь. Для нахождения приоритетного сравниваем меры
        PriorityQueue<Item> priorityQueue = new PriorityQueue<>(10, Comparator.comparing(Solver::measure));

        // шаг 1
        priorityQueue.add(new Item(null, initial));

        while (true) {
            Item board = priorityQueue.poll(); //  шаг 2

            //   если дошли до решения, сохраняем весь путь ходов в лист
            if (board.board.isGoal()) {
                itemToList(new Item(board, board.board));
                return;
            }

            //   шаг 3
            for (Board board1 : board.board.neighbors()) {
                // Чтобы не возвращаться в состояния,
                // которые уже были, делаем проверку.
                if (board1 != null && !containsInPath(board, board1))
                    priorityQueue.add(new Item(board, board1));
            }

        }
    }

    // вычисляем f(x)
    private static int measure(Item item) {
        Item item2 = item;
        int c = 0;   // g(x)
        int measure = item.getBoard().h();  // h(x)
        while (true) {
            c++;
            item2 = item2.prevBoard;
            if (item2 == null) {
                // g(x) + h(x)
                return measure + c;
            }
        }
    }

    // сохранение
    private void itemToList(Item item) {
        Item item2 = item;
        while (true) {
            item2 = item2.prevBoard;
            if (item2 == null) {
                Collections.reverse(result);
                return;
            }
            result.add(item2.board);
        }
    }

    // была ли уже такая позиция в пути
    private boolean containsInPath(Item item, Board board) {
        Item item2 = item;
        while (true) {
            if (item2.board.equals(board)) return true;
            item2 = item2.prevBoard;
            if (item2 == null) return false;
        }
    }


    private boolean isSolvable() {
        return true;
    }

    int moves() {
        if (!isSolvable()) return -1;
        return result.size() - 1;
    }

    Iterable<Board> solution() {
        return result;
    }
}