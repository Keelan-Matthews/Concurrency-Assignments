public class Vehicle extends Thread {
    private RoadNetwork roadNetwork;
    private int[] path;
    private int currentIntersection;
    private String name;

    public Vehicle(RoadNetwork _roadNetwork) {
        this.roadNetwork = _roadNetwork;
        this.path = new int[3];
        this.name = Thread.currentThread().getName();
    }

    public void setPath(int id1, int id2, int id3) {
        this.path[0] = id1;
        this.path[1] = id2;
        this.path[2] = id3;
        this.currentIntersection = -1;
    }

    @Override
    public void run() {
        for (int i = -1; i < 3; i++) {
            Intersection next = roadNetwork.getVertex(path[i + 1]);

            if (currentIntersection == -1) {
                next.enq(name);
                System.out.println(name + " enq into " + next.id);
            } else {
                Intersection current = roadNetwork.getVertex(currentIntersection);

                // wait for the light to be green
                while (!current.getEdgeState(next.id).equals("green")) {}

                next.enq(name);
                System.out.println(name + " enq into " + next.id);
                // deq from the current intersection
                current.deq();
                System.out.println(name + " deq from " + current.id);
            }
            // update the current intersection
            currentIntersection = path[i+1];
        }

    }
}
