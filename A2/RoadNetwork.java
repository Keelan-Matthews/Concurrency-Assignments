import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RoadNetwork {
    private Map<Intersection, List<Intersection>> network = new HashMap<>();

    public RoadNetwork() {
        addEdge(new Intersection(0), new Intersection(1));
        addEdge(new Intersection(0), new Intersection(2));
        addEdge(new Intersection(1), new Intersection(3));
        addEdge(new Intersection(2), new Intersection(3));

        getVertex(0).setAdjIntersections(1, 2);
        getVertex(1).setAdjIntersections(0, 3);
        getVertex(2).setAdjIntersections(0, 3);
        getVertex(3).setAdjIntersections(1, 2);
    }

    public void addEdge(Intersection source, Intersection destination) {
        if (!network.containsKey(source))
            addVertex(source);
        if (!network.containsKey(destination))
            addVertex(destination);
        network.get(source).add(destination);
        network.get(destination).add(source);
    }

    public void addVertex(Intersection s) {
        network.put(s, new LinkedList<Intersection>());
    }

    List<Intersection> getAdjVertices(int id) {
        return network.get(new Intersection(id));
    }

    public void stopLights() {
        for (Intersection i : network.keySet()) {
            i.stopLight();
        }
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
