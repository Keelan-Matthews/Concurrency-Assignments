import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int numVehicles = 1;
        RoadNetwork roadNetwork = new RoadNetwork(numVehicles);
        // make list of car names
        ArrayList<String> carNames = generateNames(numVehicles);

        Vehicle[] vehicles = new Vehicle[numVehicles];
        for (int i = 0; i < numVehicles; i++) {
            int rand = (int) (Math.random() * carNames.size());
            vehicles[i] = new Vehicle(roadNetwork, carNames.get(rand));
            carNames.remove(rand);

            vehicles[i].setName(Integer.toString(i));

            int id1 = (int) (Math.random() * 4);
            int id2 = (int) (Math.random() * 4);

            // check if vertices are adjacent
            while (!roadNetwork.areAdjacent(id1, id2) || id1 == id2) {
                id2 = (int) (Math.random() * 4);
            }

            int id3 = (int) (Math.random() * 4);
            while (!roadNetwork.areAdjacent(id2, id3) || id3 == id1 || id3 == id2) {
                id3 = (int) (Math.random() * 4);
            }

            vehicles[i].setPath(id1, id2, id3);
        }  

        // start each vehicle
        for (int i = 0; i < numVehicles; i++) {
            vehicles[i].start();
        }

        // wait for all vehicles to finish
        for (int i = 0; i < numVehicles; i++) {
            try {
                vehicles[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All vehicles have finished their paths");
        roadNetwork.stopLights();
    }

    private static ArrayList<String> generateNames(int numVehicles) {
        ArrayList<String> carNames = new ArrayList<String>();
        carNames.add("Audi");
        carNames.add("BMW");
        carNames.add("Chevrolet");
        carNames.add("Dodge");
        carNames.add("Ferrari");
        carNames.add("Ford");
        carNames.add("Honda");
        carNames.add("Hyundai");
        carNames.add("Jeep");
        carNames.add("Kia");
        carNames.add("Lexus");
        carNames.add("Mazda");
        carNames.add("Mercedes");
        carNames.add("Nissan");
        carNames.add("Porsche");
        carNames.add("Subaru");
        carNames.add("Toyota");
        carNames.add("Volkswagen");
        carNames.add("Volvo");

        // remove cars that are not needed
        for (int i = 0; i < carNames.size() - numVehicles; i++) {
            int rand = (int) (Math.random() * carNames.size());
            carNames.remove(rand);
        }

        return carNames;
    }
}