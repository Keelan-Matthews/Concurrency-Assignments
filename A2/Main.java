public class Main {
    public static void main(String[] args) {
        RoadNetwork roadNetwork = new RoadNetwork();
        // create 5 vehicles
        Vehicle[] vehicles = new Vehicle[5];
        for (int i = 0; i < 5; i++) {
            vehicles[i] = new Vehicle(roadNetwork);
            vehicles[i].setName(Integer.toString(i));
        }
        // set paths for each vehicle
        vehicles[0].setPath(0, 1, 3);
        vehicles[1].setPath(0, 2, 3);
        vehicles[2].setPath(1, 3, 2);
        vehicles[3].setPath(1, 0, 2);
        vehicles[4].setPath(2, 3, 1);
        // start each vehicle
        for (int i = 0; i < 5; i++) {
            vehicles[i].start();
        }

        // wait for all vehicles to finish
        for (int i = 0; i < 5; i++) {
            try {
                vehicles[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All vehicles have finished their paths");
    }
}