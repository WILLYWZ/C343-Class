import java.util.*;
//Check if autograder is working
public class Graph {

    private ArrayList<Item> nodes;
    private Hashtable<Item, ArrayList<Item>> neighbors;
    private Hashtable<Edge, Integer> weights;

    Graph(
            ArrayList<Item> nodes,
            Hashtable<Item, ArrayList<Item>> neighbors,
            Hashtable<Edge, Integer> weights) {
        this.nodes = nodes;
        this.neighbors = neighbors;
        this.weights = weights;
    }

    // -----

    ArrayList<Item> getNodes() {
        return nodes;
    }

    Hashtable<Item, ArrayList<Item>> getNeighbors() {
        return neighbors;
    }

    Hashtable<Edge, Integer> getWeights() {
        return weights;
    }

    // -----
    // Computes all shortest paths from a given node
    // Nodes are marked with the shortest path to the source

    void allShortestPaths(Item source) {
        // TODO
        WeakHeap weakHeap = new WeakHeap();
        for (Item n : nodes) {
            n.setPrevious(null);
            n.setVisited(false);
            n.setValue(Integer.MAX_VALUE);
            weakHeap.insert(n);
        }

        weakHeap.updateKey(source.getPosition(), 0);

        while (weakHeap.isEmpty() == false) {
            Item current = weakHeap.extractMin();
            current.setVisited(true);
            for (Item item : neighbors.get(current)) {
                if (item.isVisited() == false) {
                    int newWeight = current.getValue() +
                                    weights.get(new Edge(current, item));
                    if (newWeight < item.getValue()) {
                        item.setPrevious(current);
                        weakHeap.updateKey(item.getPosition(), newWeight);
                    }
                }
            }
        }
    }

    // -----
    // Point-to-point shortest path; stops as soon as it reaches destination

    ArrayList<Edge> shortestPath(Item source, Item dest) {
        // TODO
        WeakHeap weakHeap = new WeakHeap();
        for (Item item : nodes) {
            item.setPrevious(null);
            item.setVisited(false);
            item.setValue(Integer.MAX_VALUE);
            weakHeap.insert(item);
        }

        weakHeap.updateKey(source.getPosition(), 0);
        Item current = source;
        while (weakHeap.isEmpty() == false) {
            current = weakHeap.extractMin();
            if (current.equals(dest)){
                break;
            }
            current.setVisited(true);

            for (Item item : neighbors.get(current)) {
                if (item.isVisited() == false) {
                    int newWeight = current.getValue() +
                                    weights.get(new Edge(current, item));
                    if (newWeight < item.getValue()) {
                        item.setPrevious(current);
                        weakHeap.updateKey(item.getPosition(), newWeight);
                    }
                }
            }
        }
        return Item.pathToSource(current);
    }

    // -----

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Nodes:%n%s%n", nodes));
        res.append(String.format("Neighbors:%n%s%n", neighbors));
        res.append(String.format("Weights:%n%s%n", weights));
        return new String(res);
    }
}
