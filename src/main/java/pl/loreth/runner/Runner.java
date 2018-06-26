package main.java.pl.loreth.runner;

import main.java.pl.loreth.algorithm.Solution;

public class Runner {
    public static void main(String[] args) {
        int R[] = new int[]{2,5,5,9,14,18};
       // int R[] = new int[]{5, 8, 74, 84, 95, 96};
        //int X = 7;
        //int M = 100;
        int X = 1;
        int M = 19;

        Solution solution = new Solution();
        System.out.println(solution.solution(R, X, M));
    }
}
