package Sprint;

import java.io.*;
import java.util.*;

public class BobReleaseScheduler {

    // Class to store release information
    static class Release {
        int startDay;
        int duration;
        int endDay;

        public Release(int startDay, int duration) {
            this.startDay = startDay;
            this.duration = duration;
            this.endDay = startDay + duration - 1;
        }
    }

    public static void main(String[] args) {
        // List to store all releases from the input file
        List<Release> releases = new ArrayList<>();
        
        // Read input from releases.txt file
        try (BufferedReader reader = new BufferedReader(new FileReader("releases.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int startDay = Integer.parseInt(parts[0]);
                int duration = Integer.parseInt(parts[1]);
                releases.add(new Release(startDay, duration));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort releases by start day, then by duration
        releases.sort((r1, r2) -> {
            if (r1.startDay != r2.startDay) {
                return Integer.compare(r1.startDay, r2.startDay);
            } else {
                return Integer.compare(r1.duration, r2.duration);
            }
        });

        // List to store selected releases
        List<Release> selectedReleases = new ArrayList<>();
        int currentDay = 1;

        // Select the maximum number of releases without exceeding the sprint time (day 10)
        for (Release release : releases) {
            if (release.startDay >= currentDay && release.endDay <= 10) {
                selectedReleases.add(release);
                currentDay = release.endDay + 1;  // Move to the next available day after this release
            }
        }

        // Write output to solution.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(".\\files\\solution.txt"))) {
            writer.write(selectedReleases.size() + "\n");
            for (Release release : selectedReleases) {
                writer.write(release.startDay + " " + release.endDay + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
