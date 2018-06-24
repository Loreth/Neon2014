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


        //if there are boats to the right of bollards in the beginning, they can be ignored, as their distance can't be improved (implicated by N bollards and N boats)
        // - they also set the "minimal"  max distance
        int leftLimit = 0;
        while (leftLimit < R.length && bollardDistance[leftLimit] <= 0) {
            leftLimit++;
        }

        //minimal max distance
        maxDistance = getMaxDistance(bollardDistance, 0, leftLimit);
        //index of boat with max distance to the right
        int maxDistanceRightIndex = getMaxDistanceIndex(bollardDistance, leftLimit, bollardDistance.length - 1);
        int maxDistanceRight = bollardDistance[maxDistanceRightIndex];

        //while
        //if boat on the left doesn't exceed minimal maxDist or causes maximal distance boat to increase, there's no point in moving it, so ignore it
        if (Math.abs(bollardDistance[leftLimit]) <= maxDistance && Math.abs(maxDistanceRight + 1) > Math.abs(maxDistanceRight)) {
            leftLimit++;
            //else move all unexcluded boats to the right by one
        } else {
            incrementDistances(bollardDistance, leftLimit, bollardDistance.length - 1);
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

    //max distance for a part of boats
    private int getMaxDistance(int distances[], int from, int to) {
        int maxDistance = Math.abs(distances[0]);
        for (; from <= to; from++) {
            if (Math.abs(distances[from]) > maxDistance) maxDistance = distances[from];
        }
        return maxDistance;
    }

    private int getMaxDistanceIndex(int distances[], int from, int to) {
        int maxDistance = Math.abs(distances[0]);
        int index = from;
        for (; from <= to; from++) {
            if (Math.abs(distances[from]) > maxDistance) {
                maxDistance = distances[from];
                index = from;
            }
        }
        return maxDistance;
    }

    //increment all distances of a section by one
    private void incrementDistances(int distances[], int from, int to) {
        for (; from <= to; from++) {
            distances[from]++;
        }
    }

}
