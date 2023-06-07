package com.example;
import java.util.Arrays;

public class ActivitySelection {
    
    public static void printMaxActivities(int[] startTimes, int[] endTimes) {
        // Create an array of Activity objects from the start and end times
        Activity[] activities = new Activity[startTimes.length];
        for (int i = 0; i < startTimes.length; i++) {
            activities[i] = new Activity(startTimes[i], endTimes[i]);
        }
        
        // Sort the activities based on their end times
        Arrays.sort(activities);

        int maxActivities = 1; // At most one activity can be performed
        int lastEndTime = activities[0].endTime;
        
        // Print the first activity
        System.out.println("Start Time: " + activities[0].startTime + ", End Time: " + activities[0].endTime);

        // Iterate through the rest of the activities
        for (int i = 1; i < activities.length; i++) {
            // If the start time of the current activity is greater than or equal to the end time of the last activity
            if (activities[i].startTime >= lastEndTime) {
                maxActivities++; // Increment the count of maximum activities
                lastEndTime = activities[i].endTime; // Update the last end time
                System.out.println("Start Time: " + activities[i].startTime + ", End Time: " + activities[i].endTime);
            }
        }

        // Print the maximum number of activities that can be performed
        System.out.println("Maximum number of activities: " + maxActivities);
    }

    public static void main(String[] args) {
        int[] startTimes = {1, 3, 0, 5, 8, 5};
        int[] endTimes = {2, 4, 6, 7, 9, 9};

        printMaxActivities(startTimes, endTimes);
    }
}

class Activity implements Comparable<Activity> {
    int startTime;
    int endTime;

    public Activity(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public int compareTo(Activity other) {
        return this.endTime - other.endTime;
    }
}
