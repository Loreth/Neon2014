package main.java.pl.loreth.algorithm;


public class Solution {
    public int solution(int[] R, int X, int M) {
        //largest distance between the middle of any boat's bow and the bollard to which the boat is moored
        int maxDistance;
        int boatWidth = 2 * X;

        //If it is not possible to tie all the boats, the algorithm should return
        //happens if (boats count * boat width > wharf length)
        if (R.length * boatWidth > M) {
            return -1;
        }

        //initial setup of boat's bow positions, boats set next to each other starting from left
        int boatPositions[] = new int[R.length];
        boatPositions[0] = X;

        for (int i = 1; i < R.length; i++) {
            boatPositions[i] = boatPositions[i - 1] + boatWidth;
        }

        //initial setup of distance of mooring bollard to a corresponding boat's bow
        //distance is negative if the boat is further to the right than the bollard
        int bollardDistances[] = new int[R.length];

        for (int i = 0; i < R.length; i++) {
            bollardDistances[i] = R[i] - boatPositions[i];
        }

        //TODO: "minimal"  max distance is set by the boat with the lowest negative distance or 0 if there is none, calculate movement accordingly

        //if there are boats to the right of bollards in the beginning, they can be ignored, as their distance can't be improved (implicated by N bollards and N boats)
        int leftLimit = 0;
        while (leftLimit < R.length && bollardDistances[leftLimit] <= 0) {
            leftLimit++;
        }

        int limitingDistances[] = getLimitingDistancesArray(bollardDistances);

        //if boat on the left doesn't exceed minimal maxDist or causes maximal distance boat to increase, there's no point in moving it, so ignore it
//        while (leftLimit < R.length) {
//            if (Math.abs(bollardDistances[leftLimit]) <= maxDistance) {
//                leftLimit++;
//                //else move all unexcluded boats to the right by one
//            } else if (Math.abs(bollardDistances[maxDistanceRightIndex] - 1) > Math.abs(bollardDistances[maxDistanceRightIndex])) {
//                leftLimit = maxDistanceRightIndex + 1;
//                if (leftLimit < R.length) break;
//                maxDistanceRightIndex = getMaxDistanceIndex(bollardDistances, leftLimit, R.length - 1);
//            } else {
//                //if the last boat has reached right edge of wharf, break
//                if (R[R.length - 1] - bollardDistances[R.length - 1] + X == M) {
//                    break;
//                }
//                decrementDistances(bollardDistances, leftLimit, R.length - 1);
//            }
//        }


        return getMaxDistance(bollardDistances);
    }

    private int getMaxDistance(int distances[]) {
        int maxDistance = Math.abs(distances[0]);
        for (int dist : distances) {
            if (Math.abs(dist) > maxDistance) {
                maxDistance = Math.abs(dist);
            }
        }
        return maxDistance;
    }

    //max distance for a part of boats
    private int getMaxDistance(int distances[], int from, int to) {
        int maxDistance = Math.abs(distances[from]);
        for (; from <= to; from++) {
            if (Math.abs(distances[from]) > maxDistance) {
                maxDistance = Math.abs(distances[from]);
            }
        }
        return maxDistance;
    }

    //returns an array that specifies minimal distance (non-absolute) in a set of boats to the right of a single boat
    private int[] getLimitingDistancesArray(int distances[]) {
        int limitingDistances[] = new int[distances.length];
        int lowestDistance = distances[distances.length - 1];
        for (int i = distances.length - 1; i >= 0; i--) {
            if (lowestDistance > distances[i]) {
                lowestDistance = distances[i];
            }
            limitingDistances[i] = lowestDistance;
        }

        return limitingDistances;
    }

}
