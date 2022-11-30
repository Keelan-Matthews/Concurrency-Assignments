public class Vehicle extends Thread {
    private RoadNetwork roadNetwork;
    private int[] path;
    private int currentIntersection;
    private String name;

    public Vehicle(RoadNetwork _roadNetwork, String _name) {
        this.roadNetwork = _roadNetwork;
        this.path = new int[3];
        this.name = _name;
    }

    public String getCarName() {
        return name;
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
            // if no more in path, deq from last intersection
            if (i == 2) {
                Intersection current = roadNetwork.getVertex(currentIntersection);
                current.deq();
                break;
            }

            Intersection next = roadNetwork.getVertex(path[i + 1]);

            if (currentIntersection == -1) {
                next.enq(name);
            } else {
                Intersection current = roadNetwork.getVertex(currentIntersection);
                // wait for the light to be green
                while (!current.getEdgeState(next.id).equals("green") && !current.getEdgeState(next.id).equals("yellow") ) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                next.enq(name);
                current.deq();
            }
            // update the current intersection
            currentIntersection = path[i+1];
        }

    }
}
