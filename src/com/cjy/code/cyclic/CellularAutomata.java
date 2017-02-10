package com.cjy.code.cyclic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CellularAutomata {

    private final Board         mainBoard;

    private final CyclicBarrier barrier;

    private final Worker[]      workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {

            @Override
            public void run() {
                mainBoard.commitNewValues();
            }
        });

        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));

        }
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                        try {
                            barrier.await();
                        } catch (InterruptedException e) {
                            return;
                        } catch (BrokenBarrierException ex) {
                            return;
                        }
                    }
                }
            }
        }

        private int computeValue(int x, int y) {
            // Compute the new value that goes in (x,y)
            return 0;
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
            ;
        }
        mainBoard.waitForConvergence();
    }

    interface Board {
        int getMaxX();

        int getMaxY();

        int getValue(int x, int y);

        int setNewValue(int x, int y, int value);

        void commitNewValues();

        boolean hasConverged();

        void waitForConvergence();

        Board getSubBoard(int numPartitions, int index);
    }

    class BoardImpl implements Board {

        @Override
        public int getMaxX() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int getMaxY() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int getValue(int x, int y) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int setNewValue(int x, int y, int value) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void commitNewValues() {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean hasConverged() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void waitForConvergence() {
            // TODO Auto-generated method stub

        }

        private int numPartitions;

        @Override
        public Board getSubBoard(int numPartitions, int index) {
            // TODO Auto-generated method stub
            this.numPartitions = numPartitions;
            return null;
        }

    }

}
