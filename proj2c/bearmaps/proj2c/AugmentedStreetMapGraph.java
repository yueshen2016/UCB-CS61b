package bearmaps.proj2c;

import bearmaps.hw4.TrieST;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, James Shen
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private Map<Point, Node> pointToNode;
    private TrieST<Integer> trie;
    private Map<String, String> cleanNameToOriginalName;
    private Map<String, List<Node>> cleanNameToPossibleNodes;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        pointToNode = new HashMap<>();
        trie = new TrieST<>();
        cleanNameToOriginalName = new HashMap<>();
        cleanNameToPossibleNodes = new HashMap<>();

        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        for (Node node : nodes) {
            if (!neighbors(node.id()).isEmpty()) {
                pointToNode.put(new Point(node.lon(), node.lat()), node);
            }
            if (node.name() != null && node.name().length() != 0) {
                trie.put(cleanString(node.name()), 0);
                cleanNameToOriginalName.put(cleanString(node.name()), node.name());
                if (!cleanNameToPossibleNodes.containsKey(cleanString(node.name()))) {
                    List<Node> newList = new LinkedList<>();
                    newList.add(node);
                    cleanNameToPossibleNodes.put(cleanString(node.name()), newList);
                } else {
                    cleanNameToPossibleNodes.get(cleanString(node.name())).add(node);
                }
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        KDTree kdTree = new KDTree(new ArrayList<>(pointToNode.keySet()));
        Point nearestPoint = kdTree.nearest(lon, lat);
        return pointToNode.get(nearestPoint).id();
    }

    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanName = cleanString(prefix);
        List<String> ret = new LinkedList<>();
        for (String fullCleanName : trie.keysWithPrefix(cleanName)) {
            ret.add(cleanNameToOriginalName.get(fullCleanName));
        }
        return ret;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanName = cleanString(locationName);
        List<Node> list = cleanNameToPossibleNodes.get(cleanName);
        List<Map<String, Object>> ret = new LinkedList<>();
        for (Node n : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("lat", n.lat());
            map.put("lon", n.lon());
            map.put("id", n.id());
            map.put("name", n.name());
            ret.add(map);
        }
        return ret;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    public double distance (long prevNode, long currNode) {
        return distance(lon(prevNode), lon(currNode), lat(prevNode), lat(currNode));
    }
}
