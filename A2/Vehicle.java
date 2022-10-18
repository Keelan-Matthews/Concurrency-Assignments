import java.util.List;

public class Vehicle extends Thread {
    private RoadNetwork roadNetwork;
    private int[] path;
    private int currentIntersection;

    public Vehicle(RoadNetwork _roadNetwork) {
        this.roadNetwork = _roadNetwork;
        this.path = new int[3];
    }

    public void setPath(int id1, int id2, int id3) {
        this.path[0] = id1;
        this.path[1] = id2;
        this.path[2] = id3;
        this.currentIntersection = id1;
    }

    @Override
    public void run() {
        for (int i = 0; i < path.length; i++) {
            Intersection nextIntersection = roadNetwork.getVertex(path[i]);
            while (currentIntersection != nextIntersection.id) {
                List<Intersection> adjVertices = roadNetwork.getAdjVertices(currentIntersection);
                for (Intersection adjVertex : adjVertices) {
                    if (adjVertex.id == nextIntersection.id) {
                        currentIntersection = nextIntersection.id;
                        System.out.println("Vehicle " + this.getId() + " is at intersection " + currentIntersection);
                        break;
                    }
                }
            }
        }
    }
}
