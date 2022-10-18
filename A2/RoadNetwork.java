import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoadNetwork {
    private Map<Intersection, List<Intersection>> network;
    public RoadNetwork() {
        for (int i = 0; i < 4; i++) {
            network.put(new Intersection(i), new ArrayList<>());
        }

        this.addEdge(0, 1);
        this.addEdge(0, 2);
        this.addEdge(1, 3);
        this.addEdge(2, 3);
    }

    void addEdge(int id1, int id2) {
        network.get(new Intersection(id1)).add(new Intersection(id2));
        network.get(new Intersection(id2)).add(new Intersection(id1));
    }

    List<Intersection> getAdjVertices(int id) {
        return network.get(new Intersection(id));
    }

    Intersection getVertex(int id) {
        for (Intersection vertex : network.keySet()) {
            if (vertex.id == id) {
                return vertex;
            }
        }
        return null;
    }
}
