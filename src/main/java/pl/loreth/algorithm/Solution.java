package main.java.pl.loreth.algorithm;


public class Solution {
    public int solution(int[] R, int X, int M) {
        //largest distance between the middle of any boat's bow and the bollard to which the boat is moored
        int maxDistance;
        int boatWidth = 2 * X;

        //If it is not possible to tie all the boats, the function should return
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

        //TODO: "minimal"  max distance is set by the boat with the lowest negative distance or 0 if there is none, calculate movement accordingly

        //if there are boats to the right of bollards in the beginning, they can be ignored, as their distance can't be improved (implicated by N bollards and N boats)
        // - they also set the "minimal"  max distance
        int leftLimit = 0;
        while (leftLimit < R.length && bollardDistance[leftLimit] <= 0) {
            leftLimit++;
        }

        //minimal max distance
        //if there is more than one bollard at the same spot, it implies minimal max distance (amount of bollards at the same spot * X)
        maxDistance = (getMaxBollardsInOnePlace(R) - 1) * X;
        if (leftLimit > 0) {
            int max = getMaxDistance(bollardDistance, 0, leftLimit - 1);
            if (max > maxDistance) {
                maxDistance = max;
            }
        }

        //if all boats are on the left
        if (leftLimit == R.length) {
            return maxDistance;
        }

        //index of boat with max distance to the right
        int maxDistanceRightIndex = getMaxDistanceIndex(bollardDistance, leftLimit, R.length - 1);
        int maxDistanceRight = bollardDistance[maxDistanceRightIndex];

        //if minimal max distance is bigger than current max distance on the right, it is the best distance that can be achieved
        if (maxDistance >= maxDistanceRight) {
            return maxDistance;
        }


        //if boat on the left doesn't exceed minimal maxDist or causes maximal distance boat to increase, there's no point in moving it, so ignore it
//        while (leftLimit < R.length) {
//            if (Math.abs(bollardDistance[leftLimit]) <= maxDistance) {
//                leftLimit++;
//                //else move all unexcluded boats to the right by one
//            } else if (Math.abs(bollardDistance[maxDistanceRightIndex] - 1) > Math.abs(bollardDistance[maxDistanceRightIndex])) {
//                leftLimit = maxDistanceRightIndex + 1;
//                if (leftLimit < R.length) break;
//                maxDistanceRightIndex = getMaxDistanceIndex(bollardDistance, leftLimit, R.length - 1);
//            } else {
//                //if the last boat has reached right edge of wharf, break
//                if (R[R.length - 1] - bollardDistance[R.length - 1] + X == M) {
//                    break;
//                }
//                decrementDistances(bollardDistance, leftLimit, R.length - 1);
//            }
//        }

        while (leftLimit < R.length) {
            if (Math.abs(bollardDistance[leftLimit]) <= maxDistance) {
                leftLimit++;
                //else move all unexcluded boats to the right by one
            } else if (Math.abs(bollardDistance[leftLimit] - 1) < Math.abs(bollardDistance[leftLimit + 1]) - 1) {
                leftLimit++;
            } else if (Math.abs(bollardDistance[maxDistanceRightIndex] - 1) >= Math.abs(bollardDistance[leftLimit] - 1)) {
                leftLimit = maxDistanceRightIndex + 1;
                if (leftLimit < R.length) break;
                maxDistanceRightIndex = getMaxDistanceIndex(bollardDistance, leftLimit, R.length - 1);
            } else {
                //if the last boat has reached right edge of wharf, break
                if (R[R.length - 1] - bollardDistance[R.length - 1] + X == M) {
                    break;
                }
                decrementDistances(bollardDistance, leftLimit, R.length - 1);
            }
        }


        return getMaxDistance(bollardDistance);
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

    private int getMaxDistanceIndex(int distances[], int from, int to) {
        int maxDistance = Math.abs(distances[from]);
        int index = from;
        for (; from <= to; from++) {
            if (Math.abs(distances[from]) > maxDistance) {
                maxDistance = Math.abs(distances[from]);
                index = from;
            }
        }
        return index;
    }

    //decrement all distances of a section by one
    private void decrementDistances(int distances[], int from, int to) {
        for (; from <= to; from++) {
            distances[from]--;
        }
    }

    //helper method for finding the biggest amount of bollards at the same spot
    private int getMaxBollardsInOnePlace(int R[]) {
        int max = 1;
        int counter = 1;
        int previous = R[0];
        for (int bollard : R) {
            if (bollard != previous) {
                counter = 1;
            }
            if (max < counter) {
                max = counter;
            }
            counter++;
            previous = bollard;
        }

        return max;
    }


}
