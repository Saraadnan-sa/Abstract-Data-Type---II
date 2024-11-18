package graph;

import java.util.*;

public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    public ConcreteEdgesGraph() {
        checkRep();
    }

    private void checkRep() {
        assert vertices != null : "Vertices set should not be null";
        assert edges != null : "Edges list should not be null";
        for (Edge<L> edge : edges) {
            assert vertices.contains(edge.getSource()) : "Edge source should be in vertices set";
            assert vertices.contains(edge.getTarget()) : "Edge target should be in vertices set";
            assert edge.getWeight() > 0 : "Edge weight should be positive";
        }
    }

    @Override
    public boolean add(L vertex) {
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override
    public int set(L source, L target, int weight) {
        if (!vertices.contains(source)) add(source);
        if (!vertices.contains(target)) add(target);

        int previousWeight = 0;

        // Find existing edge and remove it if present
        Edge<L> toRemove = null;
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                previousWeight = edge.getWeight();
                toRemove = edge;
                break;
            }
        }

        if (toRemove != null) edges.remove(toRemove);

        // Add or update the edge
        if (weight > 0) {
            edges.add(new Edge<>(source, target, weight));
        }

        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(L vertex) {
        if (!vertices.contains(vertex)) return false;

        vertices.remove(vertex);

        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));

        checkRep();
        return true;
    }

    @Override
    public Set<L> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return Collections.unmodifiableMap(sources);
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> targets = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return Collections.unmodifiableMap(targets);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vertices: ").append(vertices()).append("\n");

        sb.append("Edges:\n");
        for (L source : vertices()) {
            for (Map.Entry<L, Integer> entry : targets(source).entrySet()) {
                sb.append(source).append(" -> ").append(entry.getKey())
                        .append(" (").append(entry.getValue()).append(")\n");
            }
        }

        return sb.toString();
    }

    public static class Edge<L> {
        private final L source;
        private final L target;
        private final int weight;

        public Edge(L source, L target, int weight) {
            if (source == null || target == null || weight <= 0) {
                throw new IllegalArgumentException("Invalid source, target, or weight");
            }
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public L getSource() {
            return source;
        }

        public L getTarget() {
            return target;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return source + " -> " + target + " (" + weight + ")";
        }
    }
}
