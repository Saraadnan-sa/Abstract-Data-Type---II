package graph;

import java.util.Map;
import java.util.Set;

public interface Graph<L> {

    // Returns an empty graph
    public static <L> Graph<L> empty() {
        return new ConcreteEdgesGraph<>();  // Returns an empty graph (ConcreteEdgesGraph or ConcreteVerticesGraph)
    }

    // Adds a vertex to the graph
    boolean add(L vertex);

    // Sets the edge from source to target with the specified weight
    int set(L source, L target, int weight);

    // Removes a vertex from the graph
    boolean remove(L vertex);

    // Returns the vertices of the graph
    Set<L> vertices();

    // Returns a map of source vertices and their corresponding edge weights for a given target
    Map<L, Integer> sources(L target);

    // Returns a map of target vertices and their corresponding edge weights for a given source
    Map<L, Integer> targets(L source);

}
