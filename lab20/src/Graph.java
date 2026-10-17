import java.util.*;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns the vertices that neighbor V. */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V. */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    public Graph prims(int start) {
        // TODO: YOUR CODE HERE
        if (!containsVertex(start)) {
            return null;
        }

        Graph mst = new Graph();
        mst.addVertex(start);
        HashSet<Integer> visited = new HashSet<>();
        HashMap<Integer, Edge> distFromTree = new HashMap<>();
        TreeSet<Integer> fringe = new TreeSet<>(new Comparator<Integer>() {
            public int compare(Integer v1, Integer v2) {
                int cmp = Integer.compare(distFromTree.get(v1).getWeight(),
                                          distFromTree.get(v2).getWeight());
                return cmp != 0 ? cmp : Integer.compare(v1, v2);
            }
        });

        visited.add(start);
        for (Edge e : getEdges(start)) {
            int dest = e.getDest();
            if (visited.contains(dest)) {
                continue;
            }
            Edge old = distFromTree.get(dest);
            if (old == null || e.getWeight() < old.getWeight()) {
                if (old != null) {
                    fringe.remove(dest);
                }
                distFromTree.put(dest, e);
                fringe.add(dest);
            }
        }

        while (!fringe.isEmpty()) {
            int v = fringe.pollFirst();
            Edge e = distFromTree.get(v);
            visited.add(v);
            mst.addEdge(e);

            for (Edge next : getEdges(v)) {
                int dest = next.getDest();
                if (visited.contains(dest)) {
                    continue;
                }
                Edge old = distFromTree.get(dest);
                if (old == null || next.getWeight() < old.getWeight()) {
                    if (old != null) {
                        fringe.remove(dest);
                    }
                    distFromTree.put(dest, next);
                    fringe.add(dest);
                }
            }
        }

        if (mst.getAllEdges().size() != getAllVertices().size() - 1) {
            return null;
        }
        return mst;
    }

    public Graph kruskals() {
        // TODO: YOUR CODE HERE
        Graph mst = new Graph();
        TreeSet<Integer> all = getAllVertices();
        for (int v : all) {
            mst.addVertex(v);
        }

        HashMap<Integer, Integer> parent = new HashMap<>();
        HashMap<Integer, Integer> size = new HashMap<>();
        for (int v : all) {
            parent.put(v, v);
            size.put(v, 1);
        }

        List<Edge> sortedEdges = new ArrayList<>(allEdges);
        sortedEdges.sort(new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2) {
                int cmp = Integer.compare(e1.getWeight(), e2.getWeight());
                if (cmp == 0) {
                    cmp = Integer.compare(e1.getSource(), e2.getSource());
                }
                if (cmp == 0) {
                    cmp = Integer.compare(e1.getDest(), e2.getDest());
                }
                return cmp;
            }
        });

        for (Edge e : sortedEdges) {
            int root1 = find(parent, e.getSource());
            int root2 = find(parent, e.getDest());
            if (root1 == root2) {
                continue;
            }

            if (size.get(root1) < size.get(root2)) {
                int temp = root1;
                root1 = root2;
                root2 = temp;
            }
            parent.put(root2, root1);
            size.put(root1, size.get(root1) + size.get(root2));
            mst.addEdge(e);
        }
        if (mst.getAllEdges().size() != all.size() - 1) {
            return null;
        }
        return mst;
    }

    private int find(HashMap<Integer, Integer> parent, int v) {
        int root = v;
        while (parent.get(root).intValue() != root) {
            root = parent.get(root);
        }
        while (parent.get(v).intValue() != root) {
            int next = parent.get(v);
            parent.put(v, root);
            v = next;
        }
        return root;
    }

    /* A comparator to help you compare vertices in terms of
     * how close they are to the current MST.
     * Feel free to uncomment the below code if you'd like to use it;
     * otherwise, you may implement your own comparator.
     */
//    private class PrimVertexComparator implements Comparator<Integer> {
//        private HashMap<Integer, Edge> distFromTree;
//
//        public PrimVertexComparator(HashMap<Integer, Edge> distFromTree) {
//            this.distFromTree = distFromTree;
//        }
//
//        @Override
//        public int compare(Integer o1, Integer o2) {
//            int edgeCompRes = distFromTree.get(o1).compareTo(distFromTree.get(o2));
//            if (edgeCompRes == 0) {
//                return o1 - o2;
//            }
//            return edgeCompRes;
//        }
//    }
}
