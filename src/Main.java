public class Main {


    public static void test1() {
        Graph g = new ListGraph();
        assert g.addNode("a");
        assert g.removeNode("a");
        assert !g.hasNode("a");


    }

    public static void test2() {
        Graph g = new ListGraph();
        EdgeGraph eg = new EdgeGraphAdapter(g);
        Edge e = new Edge("a", "b");
        Edge e1 = new Edge("a", "b");
        Edge e2 = new Edge("b", "a");

        assert eg.addEdge(e);
        assert eg.hasEdge(e);
    }

    public static void main(String[] args) {
        test1();
        test2();
    }
}
