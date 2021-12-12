package com.example.semesterexam.tool;

import com.example.semesterexam.manage.GameScreen;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class AstraPath {
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;

    private final Cell[][] grid;

    private final PriorityQueue<Cell> openCells;

    private final boolean[][] closedCells;

    private int startI, startJ;

    private int endI, endJ;



    public AstraPath(int[] from, int[] to, GameScreen gameScreen) {
        int [][]blocks = gameScreen.getMiniMap().getMatrix();
        int si = from[1];
        int sj = from[0];
        int ei = to[1];
        int ej = to[0];
        grid = new Cell[blocks.length][blocks[0].length];
        closedCells = new boolean[blocks.length][blocks[0].length];
        openCells = new PriorityQueue<Cell>(Comparator.comparingInt((Cell c) -> c.finalCost));

        startCell(si, sj);
        endCell(ei, ej);

        for (int i = 0; i < grid.length; i++) {
            for (int j =0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs((j - endJ));
                grid[i][j].solution = false;
            }
        }

        grid[startI][startJ].finalCost = 0;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 0) {
                    addBlockOnCell(i, j);
                }
            }
        }

        process();
    }

    public int[] next() {
        Cell cell = displaySolution();
        if (cell == null) {
            return null;
        }
        return new int[]{cell.j, cell.i};
    }

    public void addBlockOnCell(int i, int j) {
        grid[i][j] = null;
    }

    public void startCell(int i, int j) {
        startI = i;
        startJ = j;
    }

    public void endCell(int i, int j) {
        endI = i;
        endJ = j;
    }

    public void updateCostIfNeeded(Cell current, Cell t, int cost) {
        if (t == null || closedCells[t.i][t.j]) return;

        int tFinalCost = t.heuristicCost + cost;
        boolean isOpen = openCells.contains(t);

        if (!isOpen || tFinalCost < t.finalCost) {
            t.finalCost = tFinalCost;
            t.parent = current;

            if (!isOpen) {
                openCells.add(t);
            }
        }
    }

    public void process() {
//        if (grid[startI][startJ] == null) {
////            System.out.println("Khong the bat dau tai 1 blank!");
//        } else
        if (grid[startI][startJ] != null){
            openCells.add(grid[startI][startJ]);
            Cell current;
            while(true) {
                current = openCells.poll();

                if (current == null) {
                    break;
                }

                closedCells[current.i][current.j] = true;

                if (current.equals(grid[endI][endJ])) return;

                Cell t;

                if (current.i - 1 >= 0) {
                    t = grid[current.i - 1][current.j];
                    updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
                }

                if (current.j - 1 >= 0) {
                    t = grid[current.i][current.j - 1];
                    updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
                }

                if (current.j + 1 < grid[0].length) {
                    t = grid[current.i][current.j + 1];
                    updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
                }

                if (current.i + 1 < grid.length) {
                    t = grid[current.i + 1][current.j];
                    updateCostIfNeeded(current, t, current.finalCost + V_H_COST);
                }

            }
        }

    }

    public Cell displaySolution() {
        Stack<Cell> stack = new Stack<>();
        if (closedCells[endI][endJ]) {
            Cell current = grid[endI][endJ];
            grid[current.i][current.j].solution = true;
            while (current.parent != null) {
                stack.push(current);
                grid[current.parent.i][current.parent.j].solution = true;
                current = current.parent;
            }
        } else {
            return null;
        }
        if (stack.size() < 1) {
            return null;
        }
        return stack.pop();
    }

}
