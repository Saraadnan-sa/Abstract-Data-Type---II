package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import graph.Graph;

/**
 * A graph-based poetry generator.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   Represents a word affinity graph where vertices are words (case-insensitive)
    //   and edges represent adjacency with weights as the count of occurrences.
    // Representation invariant:
    //   - All vertices and edges in the graph are non-null.
    //   - Edge weights are strictly greater than 0.
    // Safety from rep exposure:
    //   The graph is private and final. No direct references are exposed.

    /**
     * Create a new poet with the graph from the given corpus.
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> lines = Files.readAllLines(corpus.toPath());
        String text = String.join(" ", lines); // Read all lines as a single text block
        String[] words = text.toLowerCase().split("\\s+"); // Split text into words by whitespace

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];

            // Update edge weight or create a new edge
            int weight = graph.targets(word1).getOrDefault(word2, 0);
            graph.set(word1, word2, weight + 1);
        }

        checkRep();
    }

    /**
     * Generate a poem from the input string by inserting bridge words.
     *
     * @param input string from which to create the poem
     * @return poem with bridge words inserted
     */
    public String poem(String input) {
        String[] words = input.split("\\s+"); // Split input into words
        StringBuilder poem = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i].toLowerCase();
            String word2 = words[i + 1].toLowerCase();

            // Add the current word to the poem
            poem.append(words[i]).append(" ");

            // Find and add a bridge word (if any)
            String bridge = findBridge(word1, word2);
            if (bridge != null) {
                poem.append(bridge).append(" ");
            }
        }

        // Add the last word
        poem.append(words[words.length - 1]);
        return poem.toString();
    }

    /**
     * Find a bridge word between two words if a valid two-edge-long path exists.
     *
     * @param word1 the first word
     * @param word2 the second word
     * @return the bridge word if one exists, otherwise null
     */
    private String findBridge(String word1, String word2) {
        String bridge = null;
        int maxWeight = 0;

        // Check all possible bridge words
        for (String candidate : graph.targets(word1).keySet()) {
            if (graph.targets(candidate).containsKey(word2)) {
                int weight = graph.targets(word1).get(candidate) + graph.targets(candidate).get(word2);
                if (weight > maxWeight) {
                    maxWeight = weight;
                    bridge = candidate;
                }
            }
        }
        return bridge;
    }

    // Check representation invariant
    private void checkRep() {
        for (String vertex : graph.vertices()) {
            assert vertex != null;
            for (String target : graph.targets(vertex).keySet()) {
                assert target != null;
                assert graph.targets(vertex).get(target) > 0;
            }
        }
    }

    @Override
    public String toString() {
        return "GraphPoet with graph: " + graph;
    }
}
