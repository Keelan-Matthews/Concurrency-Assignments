public class Vehicle extends Thread {
    private RoadNetwork roadNetwork;
    private int[] path;
    private int currentIntersection;
    private String name;

    public Vehicle(RoadNetwork _roadNetwork) {
        this.roadNetwork = _roadNetwork;
        this.path = new int[3];
        this.name = "Vehicle-" + Thread.currentThread().getName();
    }

    public void setPath(int id1, int id2, int id3) {
        this.path[0] = id1;
        this.path[1] = id2;
        this.path[2] = id3;
        this.currentIntersection = id1;
    }

    @Override
    public void run() {
        // each vehicle needs to travel to each intersection in its path and enq into its lock
        for (int i = 0; i < path.length; i++) {
            Intersection current = roadNetwork.getVertex(currentIntersection);
            Intersection next = roadNetwork.getVertex(path[i]);

            next.enq(name);
            System.out.println(name + " enq into " + next.id);
            // deq from the current intersection
            current.deq();
            System.out.println(name + " deq from " + current.id);
            // update the current intersection
            currentIntersection = path[i];
        }
    }
}
