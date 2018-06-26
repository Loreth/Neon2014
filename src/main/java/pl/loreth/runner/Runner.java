package main.java.pl.loreth.runner;

import main.java.pl.loreth.algorithm.Solution;
import main.java.pl.loreth.algorithm.Solution2;

public class Runner {
    public static void main(String[] args) {
        //int R[] = new int[]{2,5,5,9,14,18};
        int R[] = new int[]{99,99};
        int X = 45;
        int M = 195;

        Solution2 solution = new Solution2();
        System.out.println(solution.solution(R, X, M));
    }
}
