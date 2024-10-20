import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        FutureEventList eventList = new LinkedEventList();

        Scanner scanner = new Scanner(new File("simulation1.txt"));

        int hostAddress = scanner.nextInt();

        SimpleHost firstHost = new SimpleHost();

        firstHost.setHostParameters(hostAddress, eventList);
        HashMap<Integer, SimpleHost> map = new HashMap<>(); // create map to store addresses
        map.put(hostAddress, firstHost);


        int neighborAddress;
        int distance;



        while ((neighborAddress = scanner.nextInt()) != -1) {
            distance = scanner.nextInt();
            SimpleHost neighborHost = new SimpleHost();
            map.put(neighborAddress, neighborHost); // add to map


            neighborHost.setHostParameters(neighborAddress, eventList);
            firstHost.addNeighbor(neighborHost, distance); //bidirectional connection
            neighborHost.addNeighbor(firstHost, distance);
        }

        while (scanner.hasNextInt()) {
            int senderAddr = scanner.nextInt();
            int receiverAddr = scanner.nextInt();
            int interval = scanner.nextInt();
            int duration = scanner.nextInt();
            map.get(senderAddr).sendPings(receiverAddr, interval, duration); //get address from the map and call sendPings
        }

        scanner.close();
        //System.out.println(eventList.size()); //debug



        while (eventList.size() > 0) {
            Event event = eventList.removeFirst();
            event.handle();

            // System.out.println(eventList.getSimulationTime() + "\n"); //debug
            // System.out.println(eventList.size()); //debug
            // System.out.println("Handling Method: " ); //+ event.toString()); //debug
        }
    }
}
