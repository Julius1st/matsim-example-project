package automization;

import org.matsim.project.RunMatsim;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static cadyts.utilities.math.MathHelpers.round;

public class Main {
    public static void main(String[] args) {

        int[] capacities = {100};

        for(Integer currentCapacity : capacities) {
            int[] amountagents = {
                    currentCapacity,
                    };
            //round(currentCapacity *1.25),
            //                    round(currentCapacity *1.5),
            //                    round(currentCapacity *1.75),
            //                    currentCapacity *2

            //modify network.xml file
            createNetworkFile(currentCapacity, Paths.get("../../../../scenarios/automatic/network.xml"));

            for (Integer currentAmountAgents : amountagents) {
                //modify TestAgent.xml file
                createAgentFile(currentAmountAgents, Paths.get("../../../../scenarios/automatic/TestAgent.xml"));

                //run simulation
                RunMatsim.main(args);

                //create directory for output files
                Path path = Paths.get("output-storage/cap-" + currentCapacity.toString() + "/" + currentAmountAgents.toString() + "-agents.xml.gz");
                try {
                Files.createDirectories(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //new File(path.toString()).mkdir();

                //move events output to storage directory
                try {
                    java.nio.file.Files.copy(
                            new java.io.File("output/output_events.xml.gz").toPath(),
                            path,
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                            java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                            java.nio.file.LinkOption.NOFOLLOW_LINKS);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void createNetworkFile(int capacity, Path path) {
        //create network.xml file
    }

    public static void createAgentFile(int amountAgents, Path path) {
        //create TestAgent.xml file
    }
}
