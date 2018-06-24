package main.java.pl.loreth.algorithm;


import java.util.Collections;

public class Solution {
    public int solution(int[] R, int X, int M) {
        //largest distance between the middle of any boat's bow and the bollard to which the boat is moored
        int maxDistance = 0;
        int boatWidth = 2 * X;

        //If it is not possible to tie all the boats, the function should return −1
        //happens if boats count * boat width > wharf length
        if (R.length * boatWidth > M) {
            return -1;
        }

        //initial setup of boat's bow positions, boats set next to each other starting from left
        int boatPosition[] = new int[R.length];
        boatPosition[0] = X;

        for (int i = 1; i < R.length; i++) {
            boatPosition[i] = boatPosition[i - 1] + boatWidth;
        }

        //initial setup of distance of mooring bollard to a corresponding boat's bow
        //distance is negative if the boat is further to the right than the bollard
        int bollardDistance[] = new int[R.length];

        for (int i = 0; i < R.length; i++) {
            bollardDistance[i] = R[i] - boatPosition[i];
        }

        //TODO: reduce the distances


        return maxDistance;
    }

    private int getMaxDistance(int distances[]) {
        int maxDistance = Math.abs(distances[0]);
        for (int dist : distances) {
            if (Math.abs(dist) > maxDistance) maxDistance = dist;
        }
        return maxDistance;
    }

}
