import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RoadNetwork {
    private Map<Intersection, List<Intersection>> network = new HashMap<>();
    private Bakery bakery;

    public RoadNetwork(int n) {
        bakery = new Bakery(n);
        addEdge(new Intersection(0, bakery), new Intersection(1, bakery));
        addEdge(new Intersection(0, bakery), new Intersection(2, bakery));
        addEdge(new Intersection(1, bakery), new Intersection(3, bakery));
        addEdge(new Intersection(2, bakery), new Intersection(3, bakery));

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

    public boolean areAdjacent(int s, int d) {
        for (Intersection v : network.keySet()) {
            if (v.id == s) {
                for (Intersection w : network.get(v)) {
                    if (w.id == d) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void printGraph() {
        for (Intersection v : network.keySet()) {
            System.out.print(v.id + " is connected to ");
            for (Intersection w : network.get(v)) {
                System.out.print(w.id + " ");
            }
            System.out.println();
        }
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
