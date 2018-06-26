package main.java.pl.loreth.algorithm;


public class Solution {
    public int solution(int[] R, int X, int M) {
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

        //initial setup of distance of a mooring bollard to a corresponding boat's bow
        //distance is negative if the boat is further to the right than the bollard
        int bollardDistances[] = new int[R.length];

        for (int i = 0; i < R.length; i++) {
            bollardDistances[i] = R[i] - boatPositions[i];
        }


        //boats with the minimal distance (non-absolute) in a certain part [0,x] control, how much movement can be made to the right
        //the goal is not to move the boats to the right in a way, that would cause a corresponding limiting boat's absolute distance to increase further than needed
        //which effectively means - not to go below balance point between boat that's being iterated over and a corresponding limiting boat
        //(cause there is no space on the left, but there is on the right)
        //balance point (movement) is 0 (equal distances)
        //distance of boats in between doesn't matter, as they're limited by the same boat on the right, so their distance can only improve or become at worst equal to the currently moving boat
        int limitingDistances[] = getLimitingDistancesArray(bollardDistances);

        //movement of boats to the right so far, instead of updating all distances, held in a variable in order to keep good performance
        int movement = 0;
        //last possible position for middle of the last boat
        int maxBoatPosition = M - X;

        //moving boats
        for (int i = 0; i < R.length; i++) {
            //possible movement achieved by calculating balance point (hence division by 2)
            int possibleMovement = (bollardDistances[i] + limitingDistances[i]) / 2 - movement;
            if (possibleMovement > 0) {
                //if possible movement exceeds maximal possible position, move to maxBoatPosition
                if (boatPositions[R.length - 1] + movement + possibleMovement >= maxBoatPosition) {
                    movement = maxBoatPosition - boatPositions[R.length - 1];
                    for (; i < R.length; i++) {
                        bollardDistances[i] -= movement;
                    }
                    break;
                } else {
                    movement += possibleMovement;
                }
            }
            bollardDistances[i] -= movement;
        }

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
