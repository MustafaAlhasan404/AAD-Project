package com.example;

import java.util.Arrays;

public class BridgeCrossing {

    /**
     * Returns the minimum time required to transfer people across the bridge.
     * @param peopleTimes the array of times required by each person to cross the bridge
     * @return the minimum time required to transfer all people across the bridge
     */
    public static int findMinTime(int[] peopleTimes) {
        int n = peopleTimes.length;

        // Find the mask of 'n' people
        int mask = (1 << n) - 1;

        // Initialize all entries in dp as -1
        int[][] dp = new int[1 << 20][2];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        return findMinTime(mask, false, peopleTimes, n, dp);
    }

    /**
     * Returns the minimum time required to transfer people across the bridge.
     * @param leftmask the mask in which 'set bits' denotes total people standing at left side of bridge
     * @param turn denotes on which side we have to send people either from left to right (0) or from right to left (1)
     * @param peopleTimes the array of times required by each person to cross the bridge
     * @param n the number of people
     * @param dp the memoization array to store precomputed results
     * @return the minimum time required to transfer all people across the bridge
     */
    private static int findMinTime(int leftmask, boolean turn, int[] peopleTimes, int n, int[][] dp) {
        // If all people have been transferred
        if (leftmask == 0) {
            return 0;
        }

        int res = dp[leftmask][turn ? 1 : 0];

        // If we already have solved this subproblem, return the answer.
        if (res != -1) {
            return res;
        }

        // Calculate mask of right side of people
        int rightmask = ((1 << n) - 1) ^ leftmask;

        if (turn) { // currently people are at right side, thus we need to transfer people to the left side
            int minRow = Integer.MAX_VALUE, person = 0;

            for (int i = 0; i < n; ++i) {
                // Select one person whose time is less among all others present at right side
                if ((rightmask & (1 << i)) != 0) {
                    if (minRow > peopleTimes[i]) {
                        person = i;
                        minRow = peopleTimes[i];
                    }
                }
            }

            // Add that person to answer and recurse for next turn after initializing that person at left side
            res = peopleTimes[person] + findMinTime(leftmask | (1 << person), !turn, peopleTimes, n, dp);

        } else { // currently people are at left side, thus we need to transfer people to the right side
            if (Integer.bitCount(leftmask) == 1) { // only one person is present at left side, thus return that person only
                for (int i = 0; i < n; ++i) {
                    if ((leftmask & (1 << i)) != 0) {
                        res = peopleTimes[i];
                        break;
                    }
                }
            } else { // try for every pair of people by sending them to right side
                res = Integer.MAX_VALUE;

                for (int i = 0; i < n; ++i) {
                    if ((leftmask & (1 << i)) == 0) {
                        continue; // person i is not present
                    }

                    for (int j = i + 1; j < n; ++j) {
                        if ((leftmask & (1 << j)) != 0) {
                            // Find maximum time required by the two people to cross the bridge
                            int val = Math.max(peopleTimes[i], peopleTimes[j]);

                            // Recurse for other people after un-setting the ith and jth bit of left-mask
                            val += findMinTime(leftmask ^ (1 << i) ^ (1 << j), !turn, peopleTimes, n, dp);

                            // Find minimum answer among all chosen values
                            res = Math.min(res, val);
                        }
                    }
                }
            }
        }
        dp[leftmask][turn ? 1 : 0] = res;
        return res;
    }
    public static void main(String[] args) {
        int[] peopleTimes = {1, 2, 7, 10};
        System.out.println(findMinTime(peopleTimes));
    }
}