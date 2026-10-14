import java.util.*;

public class Graph {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        for (Edge e : adjLists[v1]) {
            if (e.to == v2) {
                e.weight = weight;
                return;
            }
        }
        adjLists[v1].add(new Edge(v1, v2, weight));
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        // TODO: YOUR CODE HERE
        for (Edge e : adjLists[from]) {
            if (e.to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        // TODO: YOUR CODE HERE
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (Edge e : adjLists[v]) {
            result.add(e.to);
        }
        return result;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        // TODO: YOUR CODE HERE
        int count = 0;
        for (int u = 0; u < vertexCount; u++) {
            if (isAdjacent(u, v)) {
                count++;
            }
        }
        return count;
    }

    /* Returns a list of the vertices that lie on the shortest path from start to stop. 
    If no such path exists, you should return an empty list. If START == STOP, returns a List with START. */
    public ArrayList<Integer> shortestPath(int start, int stop) {
        ArrayList<Integer> path = new ArrayList<Integer>();
        if (start == stop) {
            path.add(start);
            return path;
        }

        // Run Dijkstra's algorithm from START, keeping track of the best
        // known distance to each vertex and the vertex we came from to get it.
        int[] dist = new int[vertexCount];
        int[] pred = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        for (int v = 0; v < vertexCount; v++) {
            dist[v] = Integer.MAX_VALUE;
            pred[v] = -1;
        }
        dist[start] = 0;

        // The fringe holds candidate Edges whose weight is the tentative
        // distance from START to that Edge's "to" vertex. Stale entries are
        // skipped via VISITED, so the ordering never goes out of date.
        PriorityQueue<Edge> fringe = new PriorityQueue<Edge>(new Comparator<Edge>() {
            public int compare(Edge e1, Edge e2) {
                return e1.weight - e2.weight;
            }
        });
        fringe.add(new Edge(start, start, 0));

        while (!fringe.isEmpty()) {
            int v = fringe.poll().to;
            if (visited[v]) {
                continue;
            }
            visited[v] = true;
            for (Edge e : adjLists[v]) {
                int u = e.to;
                if (visited[u]) {
                    continue;
                }
                int candidate = dist[v] + e.weight;
                if (candidate < dist[u]) {
                    dist[u] = candidate;
                    pred[u] = v;
                    fringe.add(new Edge(v, u, candidate));
                }
            }
        }

        // STOP was never reached, so no path exists.
        if (dist[stop] == Integer.MAX_VALUE) {
            return path;
        }

        // Walk backwards from STOP to START and then reverse the result.
        LinkedList<Integer> reversed = new LinkedList<Integer>();
        for (int v = stop; v != -1; v = pred[v]) {
            reversed.addFirst(v);
        }
        path.addAll(reversed);
        return path;
    }

    /* Returns the Edge from V1 to V2, or null if no such Edge exists. */
    private Edge getEdge(int v1, int v2) {
        for (Edge e : adjLists[v1]) {
            if (e.to == v2) {
                return e;
            }
        }
        return null;
    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

        public int to() {
            return this.to;
        }

    }

    
}
